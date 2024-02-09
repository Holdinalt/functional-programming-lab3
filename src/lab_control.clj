(ns lab-control
  (:require io-control)
  (:require linear-interpolation)
  (:require logarithmic-approximation)
  (:require linear-approximation)
  (:require power-law-approximation)
  (:require quadratic-approximation)
  (:require exponential-approximation)
  (:gen-class))

(defn calcByMethod [method dots out-points]
  (cond
    (= method "linearin") (linear-interpolation/execute dots out-points)
    (= method "linearap") (linear-approximation/execute dots out-points)
    (= method "log") (logarithmic-approximation/execute dots out-points))
    (= method "power") (power-law-approximation/execute dots out-points)
    (= method "qudra") (quadratic-approximation/execute dots out-points)
    (= method "exp") (exponential-approximation/execute dots out-points)
  )

(defn calc [dots start end step method]
  (let [out-points (map #(/ %1 10) (range (* start 10) (* end 10) (* step 10)))
        result (calcByMethod method dots out-points)]

    (io-control/print-result result out-points)))

(defn find-end [mean now step]
  (cond
    (> now mean) (- now step)
    :else (recur mean (+ now step) step)))

(defn go [dots window last-count-x step methods]                         ;[] x nil
  (let [str (io-control/readLine)]
    (cond
      (= str "+D") (doseq [method methods] (lab-control/calc dots last-count-x (+ (first (last dots)) step) step method))
      :else (let [added-dots (conj (vec dots) (vec (io-control/parseDots str)))]
              (cond
                (< (count added-dots) window) (recur
                                               added-dots
                                               window
                                               (ffirst added-dots)
                                               step
                                               methods)
                :else (let [now-dots (if (> (count added-dots) window) (drop 1 added-dots) added-dots)
                            now-dots-x-sum (reduce #(+ %1 (first %2)) 0 now-dots)
                            mean (/ now-dots-x-sum window)
                            end (find-end mean last-count-x step)
                            end (if (< end (first (second now-dots))) (first (second now-dots)) end)
                            ;print (println added-dots now-dots mean end)
                            ]

                        (doseq [method methods]
                          (lab-control/calc now-dots last-count-x end step method))

                        (recur now-dots window end step methods)))))))

(defn execute [args]
  (let [trueArgs (first args)
        step (read-string (first trueArgs))
        window (read-string (second trueArgs))
        methods (vec (drop 2 trueArgs))]
    (go [] window nil step methods)))

(execute [*command-line-args*])




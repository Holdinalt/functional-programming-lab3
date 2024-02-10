(ns lab-control
  (:require io-control)
  (:require calc-control)
  (:gen-class))

(defn find-end [mean now step]
  (cond
    (> now mean) (- now step)
    :else (recur mean (+ now step) step)))

(defn go [dots window last-count-x step methods]                         ;[] x nil
  (let [str (io-control/readLine)]
    (cond
      (= str "+D") (doseq [method methods] (calc-control/calc dots last-count-x (+ (first (last dots)) step) step method))
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
                            ;pr (println methods)
                            ]

                        (doseq [method methods]
                          (calc-control/calc now-dots last-count-x end step method))

                        (recur now-dots window end step methods)))))))

(defn execute [args]
  (let [trueArgs (first args)
        step (read-string (first trueArgs))
        window (read-string (second trueArgs))
        methods (vec (drop 2 trueArgs))]
    (go [] window nil step methods)))

(execute [*command-line-args*])




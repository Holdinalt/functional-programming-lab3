(ns lab-control
  (:require input-control)
  (:require linear-interpolation)
  (:require logarithmic-approximation)
  (:gen-class)
  )

(defn calcByMethod [method dots out-points]
  (cond
    (= method "linearin") (linear-interpolation/execute dots out-points)
    (= method "log") (logarithmic-approximation/execute dots out-points)
    )
  )

(defn calc [dots start end step method]
  (let [
        out-points (range start end step)
        helo (println method)
        result (calcByMethod method dots out-points)
        ]
    (println start end step)
    (println dots out-points)
    (println result)

    (map #(println (nth out-points %1) (nth (:out result) %1)) (range (count out-points)))
    )
  )

(defn find-end [mean now step]
  (cond
    (> now mean) (- now step)
    :else (recur mean (+ now step) step)
    )
  )

(defn go [dots window last-count-x step methods]                         ;[] x nil
  (let [str (input-control/readLine)]
    (cond
      (= str "^D") (map #(calc dots last-count-x (first (last dots)) step %1) methods)
      :else (let [added-dots (conj dots (input-control/parseDots str))]
              (cond
                (< (count added-dots) window) (recur
                                                added-dots
                                                window
                                                (ffirst added-dots)
                                                step
                                                methods
                                                )
                :else (let [
                            now-dots (if (> (count added-dots) window) (drop 1 added-dots) added-dots)
                            now-dots-x-sum (reduce #(+ %1 (first %2)) 0 now-dots)
                            mean (/ now-dots-x-sum window)
                            ;hi (println now-dots now-dots-x-sum)
                            hello (println mean last-count-x step)
                            end (find-end mean last-count-x step)
                            ]
                        (do
                          (map #(lab-control/calc now-dots last-count-x end step %1) methods)
                          (recur now-dots window end step methods)
                          )
                        )
                )
              )
      )
    )
  )

(defn execute []
  ;[methods startStop step]
  (let [methods ["linearin"]
        step 0.2
        window 2
        ]
    (go [] window nil step methods)
    )
  )

(execute)



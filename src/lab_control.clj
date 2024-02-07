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
        out-points (map #(/ %1 10) (range (* start 10) (* end 10) (* step 10)))
        ;helo (println method dots out-points)
        ;met (println "method" method)
        ;met (println "dots" dots)
        ;met (println "out-start" out-points)
        ;met (println "start" start)
        result (calcByMethod method dots out-points)
        ]

    (doseq [i (range (count out-points))]
      (println (nth out-points i) (nth (:out result) i))
      )
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
                            ;hello (println mean last-count-x step)
                            ;h23 (println methods)
                            end (find-end mean last-count-x step)
                            end (if (< end (first (second now-dots))) (first (second now-dots)) end)
                            ]

                        (doseq [method methods]
                          (lab-control/calc now-dots last-count-x end step method)
                          )

                        (recur now-dots window end step methods)
                        )
                )
              )
      )
    )
  )


(defn execute [args]
  (let [trueArgs (first args)
        step (read-string (first trueArgs) )
        window (read-string(second trueArgs))
        methods (vec (drop 2 trueArgs))
        ]
    ;(println step window methods)
    (go [] window nil step methods)
    )
  )

(execute [*command-line-args*])

;(defn -main [& args]
;  (execute [args])
;  )



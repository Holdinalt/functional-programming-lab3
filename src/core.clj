(ns core
  (:require io-control)
  (:require calc-control)
  (:gen-class))

(defn find-end [mean now step]
  (cond
    (> now mean) (- now step)
    :else (recur mean (+ now step) step)))

(defn dropDotIfNeeds [dots window]
  (cond
    (> (count dots) window) (drop 1 dots)
    :else dots))

(defn go2 [dots window last-count-x step methods]

  (let [str (->> (take 1 (io-control/in)) vec first)]
    (cond
      (= str "+D") (let
                    [end (+ step (first (last dots)))
                     steps (/ (- end last-count-x) step)]
                     (doseq [method methods]
                       (->> (take steps (calc-control/getCalc method dots last-count-x step))
                            (io-control/print-result-new method (range last-count-x end step)))))

      :else (let
             [added-dots (conj (vec dots) (vec (io-control/parseDots str)))]
              (cond
                (< (count added-dots) window) (go2 added-dots window (ffirst added-dots) step methods)
                :else (let
                       [start last-count-x
                        now-dots (dropDotIfNeeds added-dots window)
                        sum (reduce #(+ %1 (first %2)) 0 now-dots)
                        mean (/ sum window)
                        end (find-end mean start step)
                        end (if (< end (first (second now-dots))) (first (second now-dots)) end)
                        steps (int (/ (- end start) step))]
                        (doseq [method methods]
                          (->> (take steps (calc-control/getCalc method now-dots start step))
                               (io-control/print-result-new method (range start end step))))
                        (recur now-dots window end step methods)))))))

(defn execute [args]
  (let [trueArgs (first args)
        step (read-string (first trueArgs))
        window (read-string (second trueArgs))
        methods (vec (drop 2 trueArgs))]
    (go2 [] window nil step methods)))

;(defn execute [args]
;  (let [trueArgs (first args)
;        step 0.5
;        window 2
;        methods ["linearin"]]
;    (go2 [] window nil step methods)))

;(execute [])

(execute [*command-line-args*])




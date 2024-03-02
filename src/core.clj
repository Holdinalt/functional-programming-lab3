(ns core
  (:require io-control)
  (:require calc-control)
  (:gen-class))

;(defn find-end [mean now step]
;  (cond
;    (> now mean) (- now step)
;    :else (recur mean (+ now step) step)))
;
(defn drop-dot-of-needs [dots window]
  (cond
    (> (count dots) window) (drop 1 dots)
    :else dots))

(defn add-dot-from-string [dots string window]
  (let
    [
     added (conj (vec dots) (vec (io-control/parseDots string)))
     new-dots (drop-dot-of-needs added window)
     ]
    new-dots
    )
  )

(defn core
  [dots window last-count-x step methods input-str]
  (println dots window last-count-x step methods input-str "iteration")
  (cond
    (= input-str "+D") (let
                         [vars (calc-control/get-vars-in-window dots last-count-x window step "end")
                          start (:start vars)
                          end (:end vars)
                          steps (:steps vars)]
                         (calc-control/cac-by-methods dots step methods start end steps)
                         {:dots dots :last-count-x end}
                         )
    (< (inc (count dots)) window) (let
                              [new-dots (conj (vec dots) (vec (io-control/parseDots input-str)))]
                              ;(println "add node")
                              {:dots new-dots :last-count-x (ffirst new-dots)}
                              )
    :else (let
            [new-dots (add-dot-from-string dots input-str window)
             vars (calc-control/get-vars-in-window new-dots last-count-x window step "middle")
             start (:start vars)
             end (:end vars)
             steps (:steps vars)
             pr (println new-dots start end steps "calc")
             ]
            (calc-control/cac-by-methods new-dots step methods start end steps)
            {:dots new-dots :last-count-x end}
            )
    )
  )


;(defn go2 [dots window last-count-x step methods]
;
;  (let [str (->> (take 1 (io-control/in)) vec first)]
;    (cond
;      (= str "+D") (let
;                    [end (+ step (first (last dots)))
;                     steps (/ (- end last-count-x) step)]
;                     (doseq [method methods]
;                       (->> (take steps (calc-control/get-calc method dots last-count-x step))
;                            (io-control/print-result-new method (range last-count-x end step)))))
;
;      :else (let
;             [added-dots (conj (vec dots) (vec (io-control/parseDots str)))]
;              (cond
;                (< (count added-dots) window) (go2 added-dots window (ffirst added-dots) step methods)
;                :else (let
;                       [start last-count-x
;                        now-dots (drop-dot-of-needs added-dots window)
;                        sum (reduce #(+ %1 (first %2)) 0 now-dots)
;                        mean (/ sum window)
;                        end (find-end mean start step)
;                        end (if (< end (first (second now-dots))) (first (second now-dots)) end)
;                        steps (int (/ (- end start) step))]
;                        (doseq [method methods]
;                          (->> (take steps (calc-control/get-calc method now-dots start step))
;                               (io-control/print-result-new method (range start end step))))
;                        (recur now-dots window end step methods)))))))

;(defn execute [args]
;  (let [trueArgs (first args)
;        step (read-string (first trueArgs))
;        window (read-string (second trueArgs))
;        methods (vec (drop 2 trueArgs))]
;    (go2 [] window nil step methods)))

;(defn execute [args]
;  (let [trueArgs (first args)
;        step 0.5
;        window 2
;        methods ["linearin"]]
;    (go2 [] window nil step methods)))

(defn execute2 [args]
  (let [trueArgs (first args)
        step 0.5
        window 2
        methods ["linearin"]]
    (reduce
      (fn
        [{:keys [dots last-count-x]} new-input]
        (core dots window last-count-x step methods new-input))
      {:dots [] :last-count-x nil}
      (io-control/in))
    )
  )

(execute2 [])

;(execute [*command-line-args*])





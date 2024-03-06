(ns core
  (:require io-control)
  (:require calc-control)
  (:gen-class))

(defn drop-dot-of-needs [dots window]
  (cond
    (> (count dots) window) (drop 1 dots)
    :else dots))

(defn add-dot-from-string [dots string window]
  (let
   [added (conj (vec dots) (vec (io-control/parseDots string)))
    new-dots (drop-dot-of-needs added window)]
    new-dots))

(defn core
  [dots window last-count-x step methods input-str]
  (cond
    (= input-str "+D") (let
                        [vars (calc-control/get-vars-in-window dots last-count-x window step "end")
                         start (:start vars)
                         end (:end vars)
                         steps (:steps vars)]
                         (calc-control/cac-by-methods dots step methods start end steps)
                         {:dots dots :last-count-x end})
    (< (inc (count dots)) window) (let
                                   [new-dots (conj (vec dots) (vec (io-control/parseDots input-str)))]
                                    {:dots new-dots :last-count-x (ffirst new-dots)})
    :else (let
           [new-dots (add-dot-from-string dots input-str window)
            vars (calc-control/get-vars-in-window new-dots last-count-x window step "middle")
            start (:start vars)
            end (:end vars)
            steps (:steps vars)]
            (calc-control/cac-by-methods new-dots step methods start end steps)
            {:dots new-dots :last-count-x end})))

(defn execute [args]
  (let [trueArgs (first args)
        step (read-string (first trueArgs))
        window (read-string (second trueArgs))
        methods (vec (drop 2 trueArgs))]
    (reduce
     (fn
       [{:keys [dots last-count-x]} new-input]
       (core dots window last-count-x step methods new-input))
     {:dots [] :last-count-x nil}
     (io-control/in))))

(execute [*command-line-args*])

;(defn execute2 [args]
;  (let [trueArgs (first args)
;        step 0.5
;        window 3
;        methods ["linearap"]]
;    (reduce
;     (fn
;       [{:keys [dots last-count-x]} new-input]
;       (core dots window last-count-x step methods new-input))
;     {:dots [] :last-count-x nil}
;     (io-control/in))))
;
;(execute2 [])




(ns logarithmic-approximation
  (:require [clojure.math :as math])
  (:require [linear-approximation]))

;Name = Логарифмическая аппроксимация
;func = fi = a * ln(x) + b

; points: [[x, y], [x, y], [x, y]]
; out-points [x, x, x]
; result {
;   :out
; }

(defn execute
  [points out-points]
  (let [dataLin (reduce #(conj %1 [(math/log (first %2))  (second %2)]) [] points)
        lin (linear-approximation/execute dataLin (reduce #(conj %1 (first %2)) [] points))
        A (:a lin)
        B (:b lin)
        out (reduce
             #(conj %1 (+ (* A (math/log %2)) B))
             []
             out-points)
        result {:a A
                :b B
                :out out}]

    result))
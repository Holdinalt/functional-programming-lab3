(ns exponential-approximation
  (:require [clojure.math :as math])
  (:require [linear-approximation]))

;Name = Экспоненциальная аппроксимация;
;func = fi = a * e^(b * x)

; points: [[x, y], [x, y], [x, y]]
; out-points [x, x, x]
; result {
;   :out
; }

(defn calc [A B X]
  (* A (math/exp (* B X)))
  )

(defn execute
  [points out-points]
  (let [dataLin (reduce #(conj %1 [(first %2) (math/log1p (second %2))]) [] points)
        lin (linear-approximation/execute dataLin (reduce #(conj %1 (first %2)) [] points))
        A (math/exp (:b lin))
        B (:a lin)
        out (reduce
             #(conj %1 (calc A B %2))
             []
             out-points)
        E (reduce
            #(conj %1 (- (calc A B (first %2)) (second %2)))
            []
            points
            )
        S (reduce
            #(+ %1 (math/pow %2 2))
            0
            E
            )


        result {:a A
                :b B
                :out out
                :s S
                }]

    result))
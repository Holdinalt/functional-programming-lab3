(ns linear-approximation
  (:require [clojure.math :as math])
  )

;Name = 'Линейная аппроксимация'
;func = 'fi = a*x + b'

; points: [[x, y], [x, y], [x, y]]
; out-points [x, x, x]
; result {
;   :out
; }

(defn execute
  [points out-points]
  (let [
        len (count points)
        SX (reduce #(+ (first %2) %1) 0 points)
        SXX (reduce #(+ (math/pow (first %2) 2) %1) 0 points)
        SY (reduce #(+ (second %2) %1) 0 points)
        SXY (reduce #(+ (* (first %2) (second %2)) %1) 0 points)
        D (- (* SXX len) (math/pow SX 2))
        D1 (- (* SXY len) (* SX SY))
        D2 (- (* SXX SY) (* SX SXY))
        a (/ D1 D)
        b (/ D2 D)

        ;meanX (/ SX len)
        ;meanY (/ SY len)
        ;RXY (reduce #(+ %2 (* (- (first %2) meanX) (- (second %2) meanY))) 0 points)
        ;RX (reduce #(+ %1 (math/pow (- (first %2) meanX) 2)) 0 points)
        ;RY (reduce #(+ %1 (math/pow (- (second %2) meanY) 2)) 0 points)

        result {
                :a a
                :b b
                :out (reduce #(conj %1 (+ (* %2 a) b)) [] out-points)
                }
        ]
    result
    )
  )
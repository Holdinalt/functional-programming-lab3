(ns linear-approximation
  (:require [clojure.math :as math]))

;Name = 'Линейная аппроксимация'
;func = 'fi = a*x + b'

; points: [[x, y], [x, y], [x, y]]
; out-points [x, x, x]
; result {
;   :out
; }

(defn calc [A B X]
  (+ (* X A) B))

(defn get-vars [points]
  (let [len (count points)
        SX (reduce #(+ (first %2) %1) 0 points)
        SXX (reduce #(+ (math/pow (first %2) 2) %1) 0 points)
        SY (reduce #(+ (second %2) %1) 0 points)
        SXY (reduce #(+ (* (first %2) (second %2)) %1) 0 points)
        D (- (* SXX len) (math/pow SX 2))
        D1 (- (* SXY len) (* SX SY))
        D2 (- (* SXX SY) (* SX SXY))
        a (/ D1 D)
        b (/ D2 D)
        E (reduce
           #(conj %1 (- (calc a b (first %2)) (second %2)))
           []
           points)
        S (reduce
           #(+ %1 (math/pow %2 2))
           0
           E)]
    {:a a
     :b b
     :S S}))

(defn execute
  [points now step]
  (let [vars (get-vars points)]
    (cons
     (calc (:a vars) (:b vars) now)
     (lazy-seq (execute points (+ now step) step)))))
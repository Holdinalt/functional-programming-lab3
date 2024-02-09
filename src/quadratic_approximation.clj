(ns quadratic-approximation
  (:require [clojure.math :as math])
  (:require [matrix]))

;Name = 'Квадратичная аппроксимация';
;func = 'fi = a * x^2 + b * x + c';

; points: [[x, y], [x, y], [x, y]]
; out-points [x, x, x]
; result {
;   :out
; }

(defn calc [A B C X]
  (+ C (* B X) (* A (math/pow X 2))))

(defn execute
  [points out-points]
  (let [N (count points)
        SX (reduce #(+ (first %2) %1) 0 points)
        SXX (reduce #(+ (math/pow (first %2) 2) %1) 0 points)
        SXXX (reduce #(+ (math/pow (first %2) 3) %1) 0 points)
        SXXXX (reduce #(+ (math/pow (first %2) 4) %1) 0 points)
        SY (reduce #(+ (second %2) %1) 0 points)
        SXY (reduce #(+ (* (first %2) (second %2)) %1) 0 points)
        SXXY (reduce #(+ (* (math/pow (first %2) 2) (second %2)) %1) 0 points)
        D (matrix/determinant
           [[N SX SXX],
            [SX SXX SXXX],
            [SXX SXXX SXXXX]])
        D1 (matrix/determinant
            [[SY SX SXX],
             [SXY SXX SXXX],
             [SXXY SXXX SXXXX]])
        D2 (matrix/determinant
            [[N SY SXX],
             [SX SXY SXXX],
             [SXX SXXY SXXXX]])
        D3 (matrix/determinant
            [[N SX SY],
             [SX SXX SXY],
             [SXX SXXX SXXY]])
        C (/ D1 D)
        B (/ D2 D)
        A (/ D3 D)
        E (reduce
           #(conj %1 (- (calc A B C (first %2)) (second %2)))
           []
           points)
        S (reduce
           #(+ %1 (math/pow %2 2))
           0
           E)
        result {:a A
                :b B
                :c C
                :out (reduce
                      #(conj %1 (calc A B C %2))
                      []
                      out-points)
                :s S
                :name "Quadratic Approximation"}]

    result))
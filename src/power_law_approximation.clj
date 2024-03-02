;(ns power-law-approximation
;  (:require [clojure.math :as math])
;  (:require [linear-approximation]))
;
;;Name = Степенная аппроксимация
;;func = fi = a * x^b
;
;; points: [[x, y], [x, y], [x, y]]
;; out-points [x, x, x]
;; result {
;;   :out
;; }
;
;(defn calc [A B X]
;  (* A (math/pow X B)))
;
;(defn execute
;  [points out-points]
;  (let [dataLin (reduce #(conj %1
;                               [(math/log (first %2))
;                                (math/log (second %2))])
;                        []
;                        points)
;        lin (linear-approximation/execute dataLin (reduce #(conj %1 (first %2)) [] points))
;        A (math/exp (:b lin))
;        B (:a lin)
;        out (reduce
;             #(conj %1 (calc A B %2))
;             []
;             out-points)
;        E (reduce
;           #(conj %1 (- (calc A B (first %2)) (second %2)))
;           []
;           points)
;        S (reduce
;           #(+ %1 (math/pow %2 2))
;           0
;           E)
;
;        result {:a A
;                :b B
;                :out out
;                :s S
;                :name "Power Law Approximation"}]
;
;    result))

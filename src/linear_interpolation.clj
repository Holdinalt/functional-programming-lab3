(ns linear-interpolation)

;Name = 'Линейная интерполяция'

; points: [[x, y], [x, y], [x, y]] sorted
; out-points [x, x, x] sorted
; result {
;   :out
; }

;[
; [1 2]
; [2 3]
; [4 1]
; [7 2]
; ]

(defn get-line
  [x points index]
  (let [now-point (nth points index)
        now-x (first now-point)
        now-y (second now-point)]
    (cond
      (> x now-x) (get-line x points (inc index))
      (= x now-x) [now-y]
      :else [(nth points (dec index)) (nth points index)])))

(defn calc-y
  [x points]
  (let [line (get-line x points 0)]                         ;[[x1 y1][x2 y2]] / [y]
    (cond
      (= (count line) 1) (first line)
      :else (let
             [dot0 (first line)
              dot1 (second line)
              x0 (first dot0)
              y0 (second dot0)
              x1 (first dot1)
              y1 (second dot1)

              A (/ (- y1 y0) (- x1 x0))
              out (+ (* A (- x x0)) y0)]
              out))))

(defn execute
  [points out-points]
  (let [out (reduce #(conj %1 (calc-y %2 points)) [] out-points)

        result {:out out
                :name "Linear Interpolation"}]

    result))
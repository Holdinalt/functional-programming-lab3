(ns calc-control
  (:require linear-interpolation)
  ;(:require logarithmic-approximation)
  (:require linear-approximation)
  ;(:require power-law-approximation)
  ;(:require quadratic-approximation)
  ;(:require exponential-approximation)
  (:require io-control))

(defn get-calc [method dots start step]
  (cond
    (= method "linearin") (linear-interpolation/execute dots start step)
    (= method "linearap") (linear-approximation/execute dots start step)
    ;(= method "log") (logarithmic-approximation/execute dots out-points)
    ;(= method "power") (power-law-approximation/execute dots out-points)
    ;(= method "qudra") (quadratic-approximation/execute dots out-points)
    ;(= method "exp") (exponential-approximation/execute dots out-points)
    ))

(defn find-end [mean now step]
  (cond
    (> now mean) (- now step)
    :else (recur mean (+ now step) step)))

;{
; :start
; :end
; :steps
; }
(defn get-vars-in-window
  [window-dots last-count window step place]
  ;(println "get-vars")
  (cond
    (= place "end") (let
                     [start last-count
                      end (+ step (first (last window-dots)))
                      steps (/ (- end last-count) step)
                      out {:start start
                           :end end
                           :steps steps}]

                      out)
    :else (let [start last-count
                ;now-dots (drop-dot-of-needs window-dots window)
                sum (reduce #(+ %1 (first %2)) 0 window-dots)
                mean (/ sum window)
                end (find-end mean start step)
                end-fix (if (< end (first (second window-dots))) (first (second window-dots)) end)
                steps (int (/ (- end-fix start) step))
                out {:start start
                     :end end-fix
                     :steps steps}]

            out)))

(defn cac-by-methods [dots step methods start end steps]
  (doseq [method methods]
    (->> (take steps (calc-control/get-calc method dots start step))
         (io-control/print-result-new method (range start end step)))))
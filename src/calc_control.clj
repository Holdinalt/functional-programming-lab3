(ns calc-control
  (:require linear-interpolation)
  (:require logarithmic-approximation)
  (:require linear-approximation)
  (:require power-law-approximation)
  (:require quadratic-approximation)
  (:require exponential-approximation)
  (:require io-control))

(defn calcByMethod [method dots out-points]
  (cond
    (= method "linearin") (linear-interpolation/execute dots out-points)
    (= method "linearap") (linear-approximation/execute dots out-points)
    (= method "log") (logarithmic-approximation/execute dots out-points))
  (= method "power") (power-law-approximation/execute dots out-points)
  (= method "qudra") (quadratic-approximation/execute dots out-points)
  (= method "exp") (exponential-approximation/execute dots out-points))

(defn calc [dots start end step method]
  (let [out-points (map #(/ %1 10) (range (* start 10) (* end 10) (* step 10)))
        result (calcByMethod method dots out-points)]
    (io-control/print-result result out-points)))
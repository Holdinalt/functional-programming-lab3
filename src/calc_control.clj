(ns calc-control
  (:require linear-interpolation)
  ;(:require logarithmic-approximation)
  (:require linear-approximation)
  ;(:require power-law-approximation)
  ;(:require quadratic-approximation)
  ;(:require exponential-approximation)
  ;(:require io-control)
  )

(defn getCalc [method dots start step]
  (cond
    (= method "linearin") (linear-interpolation/execute dots start step)
    (= method "linearap") (linear-approximation/execute dots start step)
    ;(= method "log") (logarithmic-approximation/execute dots out-points)
    ;(= method "power") (power-law-approximation/execute dots out-points)
    ;(= method "qudra") (quadratic-approximation/execute dots out-points)
    ;(= method "exp") (exponential-approximation/execute dots out-points)
    ))
(ns input-control
  (:require [clojure.string :as str])
  (:require lab-control)
  )

(defn printHelp []
  (println

    )
  )

(defn execute []
  (do
    (println
      "Введите через пробел необходимые алгоритмы
      1. Линейная апроксимация
      2. Экспоненциальная апроксимация
      3. Логорифмическая апроксимация
      4. Степенная апроксимация
      5. Линейная интерполяция
      6. Полиноминульная апроксимация второй степени"
      )
    (let [choice (map (fn [x] (read-string x)) (str/split (read-line) #" "))]
      (do
        (println
          "Введите размер шага цифрой"
          )
        (let [startStop (map (fn [x] (read-string x)) (str/split (read-line) #" "))]
          (do
            (println
              "Введите размер шага цифрой"
              )
            (let [stepFloat (float (read-string (read-line)))]
              (lab-control/execute choice startStop stepFloat)
              )
            )
          )
        )
      )
    )
  )

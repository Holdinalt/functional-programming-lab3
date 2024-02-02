(ns matrix)

;numbers: [][]
;Найти Детерминант Квадратичной матрицы по Крамеру

(defn get-elem [matrix x y]
  (-> matrix
      (nth (mod x (count matrix)))
      (nth (mod y (count (first matrix))))))

(defn determinant [matrix]
  (let [len (count matrix)]
    (reduce
     (fn [accum x]
       (+
        accum
        (-
         (reduce
          #(* %1 (get-elem matrix %2 (+ %2 x)))
          1
          (range len))
         (reduce
          #(* %1 (get-elem matrix %2 (- x %2 1)))
          1
          (range len)))))

     0
     (range len))))

(defn xy [x y]
  (reduce #(conj %1 [(nth x %2) (nth y %2)])
          []
          (range (count x))))

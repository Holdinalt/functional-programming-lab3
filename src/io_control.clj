(ns io-control
  (:require [clojure.string :as str])
  (:require [clojure.java.io :as io]))

(defn printHelp []
  (println))

(defn execute [])

(defn readDotsFromFile []
  (with-open [rdr (io/reader "F:/projects/idea/functional-programming-lab3/src/example.txt")]
    (let [raw-list (reduce #(conj %1 (str/split %2 #" ")) [] (line-seq rdr))
          dots (map #(map read-string %1) raw-list)]
      dots)))

(defn ask-dots []
  (map read-string (str/split (read-line) #" ")))

(defn print-result [result out-points]
  (println "Method:" (:name result))
  (doseq [i (range (count out-points))]
    (println (nth out-points i) (nth (:out result) i))))

(defn readLine [] (read-line))

(defn parseDots [line] (map read-string (str/split line #" ")))

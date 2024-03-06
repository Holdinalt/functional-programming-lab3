(ns io-control
  (:require [clojure.string :as str]))

(defn print-result-new [method out-points dots]
  (println "Method:" method)
  (doseq [i (range (count out-points))]
    (println (nth out-points i) (nth dots i))))

(defn parseDots [line] (vec (map #(->> %1 read-string float) (str/split line #" "))))

(defn in
  ([] (repeatedly read-line)))

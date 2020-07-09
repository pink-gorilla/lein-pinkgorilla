(ns leiningen.nbconvert
  (:require
   [pinkgorilla.import.convert-main :refer [to-gorilla]]))


(defn nbconvert
  [project & args]
  (println "converting to gorilla noteboook: " args)
  (doall (map to-gorilla args)))
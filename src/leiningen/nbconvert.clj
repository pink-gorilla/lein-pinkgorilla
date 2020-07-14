(ns leiningen.nbconvert
  (:require
   [clojure.string]
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

(def notebook-deps
  [['org.pinkgorilla/notebook-encoding "0.1.9"]])

(defn nbconvert [project & args]
  (let [file-name (last args)
        project (add-dependencies project notebook-deps)
        _ (when debug? (println "project: " project))]
    (eval/eval-in-project
     project
     `(do
        ;(taoensso.timbre/set-level! :debug)
        (pinkgorilla.import.convert-main/to-gorilla ~file-name))
     '(do ;(require 'taoensso.timbre)
        (require 'pinkgorilla.import.convert-main)))))

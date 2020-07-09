(ns leiningen.dry
  (:require
   [clojure.string]
   [configurator.tools :refer [config-project]]))


; since configuration can be modified at many places,
; for testing it is good to see the final configuration thst would
; be run.

(defn dry [project & args]
  (let [config (config-project project args)]
    (println "You would start gorilla with the following configuration:\r\n"
             (pr-str config))))
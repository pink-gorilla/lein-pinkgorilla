(ns leiningen.notebook
  (:require
   [clojure.string]
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

(def notebook-deps
  [['org.pinkgorilla/notebook-clj "0.0.8"]])

(defn notebook [project & args]
  (let [config (config-project project args)
        project (add-dependencies project notebook-deps)
        _ (when debug? (println "project: " project))]
    (eval/eval-in-project
     project
     `(do
        ;(taoensso.timbre/set-level! :debug)
        (pinkgorilla.notebook.app/start-notebook! "jetty" ~config))
     '(do ;(require 'taoensso.timbre)
        (require 'pinkgorilla.notebook.app)))))



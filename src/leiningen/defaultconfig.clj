(ns leiningen.defaultconfig
  (:require
   [clojure.string]
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

(def notebook-deps
  [['org.pinkgorilla/notebook-clj "0.0.4"]])

(defn defaultconfig [project & args]
  (let [config (config-project project args)
        project (add-dependencies project notebook-deps)
        _ (when debug? (println "project: " project))]
    (eval/eval-in-project
     project
     `(do
        ;(taoensso.timbre/set-level! :debug)
        (println "Saving config to ./resources/gorilla-config.edn ..")
        (->> pinkgorilla.notebook.config/default-notebook-config
             (clojure.pprint/pprint)
             (with-out-str)
             (spit "./resources/gorilla-config.edn")))
     '(do ;(require 'taoensso.timbre)
        (require 'clojure.pprint)
        (require 'pinkgorilla.notebook.app) ; side-effects
        (require 'pinkgorilla.notebook.config)))))



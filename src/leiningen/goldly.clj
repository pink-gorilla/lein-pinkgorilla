(ns leiningen.goldly
  (:require
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

; goldly project is bare-bones, so intentionally it does not contain useful dependencies
; like gorilla-ui, ...
; demo-goldly contains useful dependencies and a precompiled js bundle.

(def goldly-deps
  [['org.pinkgorilla/demo-goldly "0.0.3"]])


;; This is the leiningen task. It needs no arguments, and can run outside a project 
;; (assuming you've got the plugin installed in your profile).


(defn goldly [project & args]
  (let [config (config-project project args "goldly-config.edn")
        project (add-dependencies project goldly-deps)
        _ (when debug? (println "project: " project))]
    (eval/eval-in-project
     project
     `(do
        ;(taoensso.timbre/set-level! :debug)
        (goldly.app/goldly-run! ~config)
        (println "running systems: " (goldly.puppet.db/systems-response)))
     '(do ;(require 'taoensso.timbre)
        (require 'goldly.app)
        (require 'goldly.puppet.db)
        (require 'systems.help)))))


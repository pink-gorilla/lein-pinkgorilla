(ns leiningen.goldly
  (:require
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

; goldly project is bare-bones, so intentionally it does not contain useful dependencies
; like gorilla-ui, ...
; goldly-server contains useful dependencies and a precompiled js bundle.

(def goldly-deps
  [['org.pinkgorilla/goldly-server "0.2.26"]]) 

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
        (goldly-server.app/goldly-server-run! "jetty" ~config) ; 
        ;(println "running systems: " (goldly.puppet.db/systems-response))
        )
     '(do ;(require 'taoensso.timbre)
        (require 'goldly-server.app)
        ;(require 'goldly.puppet.db)
        ;(require 'systems.help)
        ))))


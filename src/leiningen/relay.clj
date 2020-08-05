(ns leiningen.relay
  (:require
   [clojure.string]
   [leiningen.core.eval :as eval]
   [configurator.tools :refer [debug? add-dependencies config-project]]))

(def jetty-relay-deps
  [['nrepl "0.8.0"]  ; 0.7.0 lacks add-middleware
   ['org.clojure/core.async "1.3.610"]
   ['com.taoensso/timbre "4.10.0"]
   ['org.pinkgorilla/nrepl-middleware "0.3.12"]
   ['ring "1.8.1"]
   ['ring-cors "0.1.13"]
   ['ring/ring-defaults "0.3.2" :exclusions ['javax.servlet/servlet-api]]
   ['info.sunng/ring-jetty9-adapter "0.14.0"]])

(defn relay [project & args]
  (let [config (config-project project args "relay-config.edn")
        backend-config (:backend config)
        project (add-dependencies project jetty-relay-deps)
        _ (when debug? (println "project: " project))]
    (eval/eval-in-project
     project
     `(do
        ;(taoensso.timbre/set-level! :debug)
        (pinkgorilla.nrepl.service.nrepl-server/run-nrepl-server ~backend-config)
        (pinkgorilla.nrepl.service.relay-jetty/run-relay-jetty ~backend-config))
     '(do ;(require 'taoensso.timbre)
        (require 'pinkgorilla.nrepl.service.nrepl-server)
        (require 'pinkgorilla.nrepl.service.relay-jetty)))))



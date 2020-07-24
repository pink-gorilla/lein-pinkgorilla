(ns configurator.tools
  (:require
   [clojure.java.io :as io]
   [cprop.core :refer [load-config]]
   [cprop.source :as source]
   [configurator.cli :refer [cli-config]]))

(def debug? false)

(defn add-dependencies
  "Adds dependencies to the end of the current vector."
  [project deps]
  (update-in project [:dependencies] concat deps))

; config
; order of config modification
; 1. default config
; 2. leiningen config
; 3. environment-vars
; 4. commandline args

; https://github.com/tolitius/cprop
; By default cprop will merge all configurations it can find 
; in the following order:
; 1. classpath resource config
; 2. file on a file system (pointed by a conf system property or by (load-config :file <path>))
; 3. custom configurations, maps from various sources, etc.
; 4. System properties
; 5. ENV variables


(defn from-file [path]
  (let [file (io/file path)]
    (if (and file (.exists file))
      (source/from-file path)
      {})))

(defn config-project
  "returns a map which represents the configuration"
  ([project cli-args]
   (config-project project cli-args "notebook-config.edn"))
  ([project cli-args resource-config-edn]
   (let [lein-config (or (:pinkgorilla project) {})]
     (load-config :resource resource-config-edn
                  :merge
                  [;(source/from-resource "gorilla-config.edn")
                  ;(from-file "gorilla-config.edn")
                   lein-config
                  ;(source/from-system-props)
                  ;(source/from-env)
                   (cli-config cli-args)]))))




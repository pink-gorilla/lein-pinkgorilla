(ns leiningen.pinkgorilla
  (:require
   [leiningen.core.eval :as eval]
   ;[pinkgorilla.notebook-app.cli :refer [parse-opts]]
   ;[pinkgorilla.notebook-app.core :refer [run-gorilla-server]]
   ))

;; The version of PinkGorilla that we will use
(def pinkgorilla-version "0.4.17")

;; This is the leiningen task. It needs no arguments, and can run outside a project 
;; (assuming you've got the plugin installed in your profile).
(defn pinkgorilla
  [project & opts]
  (let [opts-map (apply hash-map opts)
        port (read-string (or (get opts-map ":port") "0"))
        ip (or (get opts-map ":ip") "127.0.0.1")
        nrepl-port (read-string (or (get opts-map ":nrepl-port") "0"))
        c (or (get opts-map ":config") "config.edn")
        ;; inject the gorilla-repl dependency into the target project
        curr-deps (or (:dependencies project) [])
        new-deps (conj curr-deps ['org.pinkgorilla/gorilla-notebook pinkgorilla-version])
        prj (assoc project :dependencies new-deps)
        project-name (:name project)
        gorilla-options (:gorilla-options project)]
    (eval/eval-in-project
     prj
     `(pinkgorilla.notebook-app.core/run-gorilla-server {:port ~port
                                                         :ip ~ip
                                                         :nrepl-port ~nrepl-port
                                                         :version ~pinkgorilla-version
                                                         :project ~project-name
                                                         :gorilla-options ~gorilla-options
                                                         :c c})
     '(require 'pinkgorilla.notebook-app.core))))


#_(defn run-notebook []
    (let [args2 ["-c" "./profiles/notebook/config.edn"]
          {:keys [options]} (parse-opts args2)]
      (println "Options Are: " options)
      (run-gorilla-server options)
      nil))
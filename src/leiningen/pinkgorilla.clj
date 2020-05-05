(ns leiningen.pinkgorilla
  (:require
   [leiningen.core.eval :as eval]))

;; The version of PinkGorilla that we will use
(def pinkgorilla-version "0.4.17")

;; This is the leiningen task. It needs no arguments, and can run outside a project 
;; (assuming you've got the plugin installed in your profile).
(defn pinkgorilla
  [project & opts]
  (let [opts-project (or (:pinkgorilla project) {})
        ;; inject the gorilla-repl dependency into the target project
        curr-deps (or (:dependencies project) [])
        new-deps (conj curr-deps ['org.pinkgorilla/gorilla-notebook pinkgorilla-version])
        prj (assoc project :dependencies new-deps)]
    (eval/eval-in-project
     prj
     `(pinkgorilla.notebook-app.core/run-gorilla-server 
         (merge (pinkgorilla.notebook-app.cli/parse-opts ~opts) ~opts-project))
     '(require 'pinkgorilla.notebook-app.core))))


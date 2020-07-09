(defproject org.pinkgorilla/lein-pinkgorilla "0.0.15"
  :description "A Leiningen plugin for the PinkGorilla Notebook."
  :url "https://github.com/pink-gorilla/lein-pinkgorilla"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]
  :min-lein-version "2.9.1"
  :min-java-version "1.11"

  :release-tasks [["vcs" "assert-committed"]
                  ["bump-version" "release"]
                  ["vcs" "commit" "Release %s"]
                  ["vcs" "tag" "v" "--no-sign"]
                  ["deploy"]
                  ["bump-version"]
                  ["vcs" "commit" "Begin %s"]
                  ["vcs" "push"]]

  :resource-paths ["resources"]

  :eval-in-leiningen true

  :managed-dependencies [[org.clojure/clojure "1.10.1"]
                         [org.clojure/core.async "1.2.603"]
                         [org.clojure/clojurescript "1.10.773"]
                         [nrepl "0.8.0-alpha5"]  ; 0.7.0 lacks add-middleware 
                         [com.cognitect/transit-clj "1.0.324"]
                         [com.cognitect/transit-cljs "0.8.264"]
                         [com.fasterxml.jackson.core/jackson-core "2.11.0"]
                         [cheshire "5.10.0"]
                         [org.clojure/tools.reader "1.3.2"]]

  :dependencies [[nrepl "0.8.0-alpha5"]  ; 0.7.0 lacks add-middleware
                 [org.clojure/tools.cli "1.0.194"] ; commandline args
                 [cprop "0.1.17"] ; configuration
                 ]

  :aliases {"bump-version"
            ["change" "version" "leiningen.release/bump-version"]})


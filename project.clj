(defproject org.pinkgorilla/lein-pinkgorilla "0.0.22-SNAPSHOT"
  :description "A Leiningen plugin for the PinkGorilla Notebook."
  :url "https://github.com/pink-gorilla/lein-pinkgorilla"
  :license {:name "MIT"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/release_username
                                     :password :env/release_password
                                     :sign-releases false}]]
  :min-lein-version "2.9.1"
  ;:min-java-version "1.11"

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

  #_:managed-dependencies
  #_[[org.clojure/clojure "1.10.1"]
     [org.clojure/core.async "1.2.603"]
     [org.clojure/clojurescript "1.10.773"]
     [org.clojure/tools.reader "1.3.2"]
     [org.clojure/tools.analyzer "1.0.0"]
     [com.google.javascript/closure-compiler-unshaded "v20200406"]
     [com.google.code.findbugs/jsr305 "3.0.2"]
     [org.clojure/tools.logging "1.0.0"]

     [nrepl "0.8.0-alpha5"]  ; 0.7.0 lacks add-middleware 

                         ; shadow-cljs
     [thheller/shadow-cljs "2.8.94"] ; 106
     [org.jboss.logging/jboss-logging "3.4.1.Final"]

                         ; serialization libraries are dependencies of many libraries,
     [org.clojure/core.memoize "0.8.2"]
     [org.clojure/data.json "1.0.0"]
     [org.clojure/data.fressian "1.0.0"]
     [org.clojure/data.xml "0.0.8"]
     [org.clojure/data.csv "1.0.0"]
     [org.clojure/core.match "1.0.0"]
     [com.cognitect/transit-clj "1.0.324"]
     [com.cognitect/transit-cljs "0.8.264"]
     [com.cognitect/transit-java "1.0.343"]
     [com.fasterxml.jackson.core/jackson-core "2.11.0"]
     [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.10.2"]
     [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.10.2"]

     [cheshire "5.10.0"]
     [com.taoensso/encore "2.119.0"]
                         ; patches to get most uptodate version for certain conflicts:
     [commons-codec "1.12"] ; selmer and clj-http (via gorilla-explore)
     [ring/ring-codec "1.1.1"] ; ring and compojure
     [org.flatland/useful "0.11.6"] ; clojail and ring-middleware-format
     [tigris "0.1.2"]]


  :dependencies [[nrepl "0.8.0"]  ; 0.7.0 lacks add-middleware
                 [org.clojure/tools.cli "1.0.194"] ; commandline args
                 [cprop "0.1.17"] ; configuration
                 ]


  :profiles {; plugins fuck-up notebook. Therefore dev2 profile
             :dev2  {:dependencies [[clj-kondo "2020.07.29"]]
                     :plugins      [[lein-cljfmt "0.6.6"]
                                    [lein-cloverage "1.1.2"]
                                    [lein-resource "17.06.1"]
                                    [lein-ancient "0.6.15"]]
                     :aliases      {"clj-kondo"
                                    ["run" "-m" "clj-kondo.main"]

                                    "bump-version" ^{:doc "Increases project.clj version number (used by CI)."}
                                    ["change" "version" "leiningen.release/bump-version"]}
                     :cloverage    {:codecov? false ; https://github.com/codecov/example-clojure
                                  ;; In case we want to exclude stuff
                                  ;; :ns-exclude-regex [#".*util.instrument"]
                                  ;; :test-ns-regex [#"^((?!debug-integration-test).)*$$"]
                                    }
                   ;; TODO : Make cljfmt really nice : https://devhub.io/repos/bbatsov-cljfmt
                     :cljfmt       {:indents {as->                [[:inner 0]]
                                              with-debug-bindings [[:inner 0]]
                                              merge-meta          [[:inner 0]]
                                              try-if-let          [[:block 1]]}}}}

  :aliases {"bump-version"
            ["change" "version" "leiningen.release/bump-version"]

            "cljfmt"
            ["with-profile" "dev2" "cljfmt"]

            "ancient"
            ["with-profile" "dev2" "ancient"]})


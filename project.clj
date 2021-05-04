(defproject org.pinkgorilla/lein-pinkgorilla "0.0.24"
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
  :dependencies [[org.clojure/tools.cli "1.0.194"] ; commandline args
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


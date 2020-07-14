
(ns configurator.cli
  "commandline (cli) options to configure a standalone pinkgorilla notebook"
  (:require
   [clojure.tools.cli :as cli]
   [clojure.string :as string]))

(def cli-options
  ;; see https://github.com/clojure/tools.cli#example-usage
  [["-p" "--nrepl-port PORT" "Port number of the nrepl server."
    ;:default nil
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]

   ["-h" "--nrepl-host HOST" "host of the nrepl server."
    ;:default nil
      ;; Specify a string to output in the default column in the options summary
      ;; if the default value's string representation is very ugly
    :default-desc "none"]

   ["-P" "--port PORT" "Port number of the GorillaNotebook web server."
    ;:default 9000
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]

   ["-H" "--ip IP" "IP of the GorillaNotebook web server."
    ;:default "localhost"
    :default-desc "localhost"]

   ["-c" "--runtime-config runtime-config" "Runtime config edn file"
    ;:default nil
    ]

   [nil "--help"]])

(defn usage [options-summary]
  (->> ["Gorilla Config"
        "Configuration gets modified in the following order"
        ""
        "1. resource gorilla-config.edn (default config)"
        "2. file gorilla-config.edn"
        "3. :pinkgorilla in leiningen project.clj"
        "4. system properties "
        "5. environment variables"
        "6. commandline parameter"
        ""
        "Commandline parameters:"
        options-summary
        ""
        ""]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn parse-opts [args]
  (let [{:keys [options errors summary] :as parsed} (cli/parse-opts args cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      ;(not= (count arguments) 1) (exit 1 (usage summary))
      (not-empty errors) (exit 1 (error-msg errors)))
    parsed))

(defn cli-config [args]
  (:options (parse-opts args)))

(comment

  (let [args ["-C" "CONFIG.EDN"]]
    (parse-opts args))
  (parse-opts ["-p" "3008"])
  (parse-opts ["-p" "3008" "-h" "127.0.0.1"])
  (cli-config ["-p" "3008" "-h" "127.0.0.1"])
  (cli-config [])
  (cli-config ["--help"])

;
  )
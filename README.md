# lein-pinkgorilla
 [![GitHub Actions status |pink-gorilla/lein-pinkgorilla](https://github.com/pink-gorilla/lein-pinkgorilla/workflows/CI/badge.svg)](https://github.com/pink-gorilla/lein-pinkgorilla/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/lein-pinkgorilla.svg)](https://clojars.org/org.pinkgorilla/lein-pinkgorilla)

- This leiningen plugin makes it easy to run pinkgorilla notebook, goldly, nrepl-relay, etc.
- Since it is a leiningen profile, you can either install it in your user profile, 
  or you can add it to your project.
- A demo project that uses lein-pinkgorilla is [ta](https://github.com/pink-gorilla/trateg)

# Installation

## user profile

Just add the following to your ~/.lein/profiles.clj

```
{:user {:plugins [[org.pinkgorilla/lein-pinkgorilla "0.0.17"]]}}
```

## in your leiningen project (project.clj)

To use PinkGorilla in one of your Leiningen projects,  add the following to the :plugins section of that project’s project.clj file:

```
[lein-pinkgorilla "0.0.17"]
```

Your completed project.clj file might look something like this:

```
(defproject your-demo "0.1.0-SNAPSHOT"
  :description "A demo project for PinkGorilla Notebook."
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot demo.core
  :target-path "target/%s"
  :plugins [[org.pinkgorilla/lein-pinkgorilla "0.0.17"]]
  :profiles {:uberjar {:aot :all}})
```

# Run

## notebook

`lein notebook` runs the notebook. It can be run either
via user profile install, or as plugin inside your project.clj. If you 
use it inside your project, the repl will have your additional
dependences available in the notebook witout haing to use `add-dependency`.
See configuration below.

## nbconvert

Convert Jupyter / Clojure files to pinkgorilla notebook:

```
lein pinkgorilla nbconvert demo.ipynb
lein nbconvert demo.clj
```

## relay

`lein relay` runs an nrepl relay without any other frontend.
Usful for testing.

# Configuration

We ship a **default configuration**, which you can run out-of-the-box - no need to make changes! But if you want to change the configuration, you have several options:
  - In project.clj add {:pinkgorilla {:options-to-override "here"}}
  - In commandline. Run `lein nbconfig --help` to see available commandline options
  - In the file gorilla-config.edn (in the same path as project.clj)
  - Environment variables. Please refer to https://github.com/tolitius/cprop to understand how to define nested configuration options in environment variables.

Run `lein nbconfig` to see the current configuration. If you did not make any
changes yet, then this will give you an idea which options you might want to override.

If you already made canges to the configuration, it allows you to check if your
configuration options did get applied correctly.

If you are a developer and want to rebuild the default config, then `lein defaultconfig` ran inside the leiningen project will do the job.

# lein-pinkgorilla
 [![GitHub Actions status |pink-gorilla/lein-pinkgorilla](https://github.com/pink-gorilla/lein-pinkgorilla/workflows/CI/badge.svg)](https://github.com/pink-gorilla/lein-pinkgorilla/actions?workflow=CI)[![Clojars Project](https://img.shields.io/clojars/v/org.pinkgorilla/lein-pinkgorilla.svg)](https://clojars.org/org.pinkgorilla/lein-pinkgorilla)

# Running Gorilla Notebook via "lein pinkgorilla"

## inside project.clj

To use Gorilla in one of your Leiningen projects,  add the following to the :plugins section of that project’s project.clj file:

```
[lein-pinkgorilla "0.0.9"]
```

Your completed project.clj file might look something like this:

```
(defproject your-demo "0.1.0-SNAPSHOT"
  :description "A demo project for PinkGorilla Notebook."
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :main ^:skip-aot demo.core
  :target-path "target/%s"
  :plugins [[org.pinkgorilla/lein-pinkgorilla "0.0.9"]]
  :profiles {:uberjar {:aot :all}})
```

That’s it. You should now be able to run ```lein pinkgorilla``` from within the project directory and get started.

## in user profile

Alternatively, just add the following to your ~/.lein/profiles.clj

{:user {:plugins [[org.pinkgorilla/lein-pinkgorilla "0.0.9"]]}}

A demo project that uses lein-pinkgorilla is [ta](https://github.com/pink-gorilla/trateg)


# lein nbconvert

Convert Jupyter / Clojure files to pinkgorilla notebook:

```
lein nbconvert demo.ipynb
lein nbconvert demo.clj
```

# lein dry

This allows you to configure gorilla, but instead of running something,
it will just print the resulting config to the commandline.




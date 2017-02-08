(defproject lein-dynamic-version "0.1.1"
  :description "Populate your leiningen project version from external sources"
  :url "https://github.com/pelletier/lein-dynamic-version"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :repositories [["releases" {:url "https://clojars.org/repo" :creds :gpg}]]
  :profiles {:clojure-1.8.0 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :clojure-dev {:dependencies [[org.clojure/clojure "1.9.0-alpha14"]]}})

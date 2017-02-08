(ns leiningen.dynamic-version)

(defn dynamic-version
  "Display the project version"
  [project & args]
  (println (:version project)))

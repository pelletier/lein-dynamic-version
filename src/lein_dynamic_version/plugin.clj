(ns lein-dynamic-version.plugin
  (:require [leiningen.core.main :as lein]
            [clojure.string :as string]))

(defn env-version [env-variable]
  (when env-variable
    (System/getenv env-variable)))

(defn file-version [file-path]
  (when file-path
    (try
      (slurp file-path)
      (catch java.io.FileNotFoundException _))))

(def loader-map
  {:env env-version
   :file file-version
   :default identity})

(defn post-process [version]
  (string/trim version))

(defn load-version [options loaders]
  (when-let [loader (first loaders)]
    (let [loader-fn (loader-map loader)
          loader-option (options loader)
          version (loader-fn loader-option)]
      (if (nil? version)
        (recur options (rest loaders))
        {:loader loader :option loader-option :version (post-process version)}))))

(defn compute-version [options]
  (let [loaders (:order options)
        version-info (load-version options loaders)]
    (when (some? version-info)
      (lein/debug "dynamic-version found version" (:version version-info) "from" (:loader version-info) (:option version-info))
      (:version version-info))))

(defn middleware [project]
  (let [default-version (:version project)
        defaults {:default default-version
                  :order [:env :file :default]}
        options (merge defaults (:dynamic-version project))
        version (compute-version options)]
    (if (nil? version)
      (lein/abort "dynamic-version cannot find version number with options" options)
      (assoc project :version version))))

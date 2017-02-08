(ns lein-dynamic-version.t-plugin
  (:require [lein-dynamic-version.plugin :as plugin])
  (:import [clojure.lang ExceptionInfo]
           [java.io File])
  (:use [clojure.test]))

(deftest middleware
  (testing "empty order"
    (with-bindings {#'leiningen.core.main/*exit-process?* false}
      (is (thrown-with-msg? ExceptionInfo #"dynamic-version cannot find version number" (plugin/middleware {:dynamic-version {:order []}})))))
  (testing "load from environment variable"
    (with-bindings {#'plugin/get-env (partial get {"FOO" "2.0.0"})}
      (is (= "2.0.0" (:version (plugin/middleware {:dynamic-version {:env "FOO"}}))))))
  (testing "load from file"
    (let [file (File/createTempFile "filename" ".txt")]
      (spit file "1.0.0")
      (is (= "1.0.0" (:version (plugin/middleware {:dynamic-version {:file (.getAbsolutePath file)}}))))))
  (testing "use default"
    (is (= "0.0.0" (:version (plugin/middleware {:version "0.0.0"})))))
  (testing "change order"
    (with-bindings {#'plugin/get-env (partial get {"FOO" "2.0.0"})}
      (is (= "0.0.0" (:version (plugin/middleware {:version "0.0.0"
                                                   :dynamic-version {:env "FOO" :order [:default :env]}})))))))

(deftest get-env
  (testing "returns an environment variable"
    (is (< 0 (count (plugin/get-env "PATH"))))))

(ns leiningen.t-dynamic-version
  (:require [leiningen.dynamic-version :as dynamic-version])
  (:use [clojure.test]))

(deftest dynamic-version
  (testing "displays the version"
    (is (= "foo\n" (with-out-str (dynamic-version/dynamic-version {:version "foo"}))))))

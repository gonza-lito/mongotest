(ns mongotest.core-test
    (:require
    [pjstadig.humane-test-output]
    [cljs.test :refer-macros [is are deftest testing use-fixtures]]
    [mongotest.core]))

(deftest test-core
  (is (= true true)))



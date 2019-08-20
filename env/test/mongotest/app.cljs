(ns mongotest.app
  (:require
    [doo.runner :refer-macros [doo-tests]]
    [mongotest.core-test]))

(doo-tests 'mongotest.core-test)



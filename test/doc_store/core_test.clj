(ns doc-store.core-test
  (:use midje.sweet)
  (:require [doc-store.core :refer :all]))


(fact "can connect to mongo"
      (store-doc {:test "data"}) => (contains {:test "data"}))

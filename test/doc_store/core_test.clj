(ns doc-store.core-test
  (:use midje.sweet)
  (:require [doc-store.core :refer :all]))


(with-state-changes [(before :facts (db-connect!))
                     (after :facts (db-disconnect!))]
  (facts "mongo"
         (fact "can add data to mongo"
               (store-doc {:test "data"}) => (contains {:test "data"}))

         (fact "can get data from mongo"
               (> (count (get-docs {:test "data"})) 0) => true)
               ))

(ns doc-store.core
  (:use [monger.core :only [connect! connect set-db! get-db]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
)

(defn store-doc [document]
  (let [^MongoOptions opts (mg/mongo-options :threads-allowed-to-block-for-connection-multiplier 300)
        ^ServerAddress sa  (mg/server-address "127.0.0.1" 27017)]
    (mg/connect! sa opts)
    (set-db! (mg/get-db "monger-test"))
    (mc/insert-and-return "documents" document)
))

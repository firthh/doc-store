(ns doc-store.core
  (:use [monger.core :only [connect! connect set-db! get-db]])
  (:require [monger.core :as mg]
            [monger.collection :as mc])
)

(def store-name "documents")

(defn- create-string-id [mongo-doc]
  (-> (merge mongo-doc {:id (.toString (:_id mongo-doc))})
      (dissoc :_id))
  )

(defn db-connect! []
  (let [uri (get (System/getenv) "MONGODB_URI" "mongodb://127.0.0.1/monger-test")]
    (mg/connect-via-uri! uri)))

(defn db-disconnect! []
  (mg/disconnect!))

(defn store-doc 
  ([document] (store-doc document store-name))
  ([document store]
      (-> (mc/insert-and-return store document)
          (create-string-id)
          )))  

(defn get-docs
  ([filter-values] (get-docs filter-values store-name))
  ([filter-values store]
      (->> (mc/find-maps store filter-values) 
           (map create-string-id)
           ))
)


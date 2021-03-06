(ns doc-store.web
  (:use compojure.core
        ring.middleware.json
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.data.json :refer (read-json json-str)]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty]
            [doc-store.core :as core])
  (:gen-class))

(defroutes app-routes
  (POST "/" {params :params}
        (json-str (core/store-doc params)))
  (POST "/merchant/:merchant" {params :params {merchant :merchant} :merchant-id}
        (json-str (core/store-doc params (str "merchant-" (:merchant params)))))
  (GET "/" [] 
       (json-str (core/get-docs {})))
  (GET "/merchant/:merchant" [merchant]
       (json-str (core/get-docs {} (str "merchant-" merchant))))
  (route/not-found (json-str {:error "not found"})))

(defn init []
  (core/db-connect!))

(defn destroy []
  (core/db-disconnect!))

(def app
  (->
   (handler/site app-routes)
   (wrap-json-params)
   (wrap-json-response)))


(defn -main
  ([] (-main 80))
  ([port]
      (init)
      (jetty/run-jetty app {:port (Integer. port) :join? false})))

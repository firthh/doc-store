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
)

(defroutes app-routes
  (POST "/" {params :params}
        (json-str (core/store-doc params)))
  (GET "/:userid" [user-id] 
       (json-str (core/get-docs {})))
  (route/not-found (json-str {:error "not found"}))
  )

(defn init []
  (core/db-connect!))

(defn destroy []
  (core/db-disconnect!))

(def app
  (->
   (handler/site app-routes)
   (wrap-json-params)
   (wrap-json-response)))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))

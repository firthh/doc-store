(ns doc-store.web
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.data.json :refer (read-json json-str)]
            [clj-transform.core :as core]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty])
)


(defroutes app-routes
  (POST "/:userid" [user-id] {:user-id user-id})
  (GET "/:userid" [user-id] {:user-id user-id})
  (route/not-found (json-str {:error "not found"}))
  )

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))

(ns doc-store.web
  (:use compojure.core
        ring.middleware.json
        ring.util.response)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.data.json :refer (read-json json-str)]
            [ring.util.response :as resp]
            [ring.adapter.jetty :as jetty])
)


(defroutes app-routes
  (POST "/" {params :params} (do
                               (println params)
                               (json-str {:stuff params})))
  (GET "/:userid" [user-id] {:user-id user-id})
  (route/not-found (json-str {:error "not found"}))
  )

(def app
  (->
   (handler/site app-routes)
   (wrap-json-params)
   (wrap-json-response)))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))

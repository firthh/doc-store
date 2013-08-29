(defproject doc-store "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url ""
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [org.clojure/data.json "0.2.2"]
                 [http.async.client "0.5.2"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-json "0.2.0"]
                 [ring "1.1.8"]
                 [com.novemberain/monger "1.5.0"]]
  :plugins [[lein-ring "0.8.5"]]
  :min-lein-version "2.0.0"
  :uberjar-name "doc-store.jar"
  :main doc-store/web
  :ring {:handler doc-store.web/app
         :init    doc-store.web/init
         :destroy doc-store.web/destroy}
  :profiles {:dev {
                   :plugins [[lein-midje "3.0.0"]
                             [lein-ancient "0.4.0"]]
                   :dependencies [[midje "1.5.1"]
                                  ]}
             })

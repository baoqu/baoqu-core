(ns baoqu-core.handlers.event
  (:require [cheshire.core :refer [generate-string]]))

(defn show
  [ctx]
  (let [id (get-in ctx [:route-params :id])]
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (generate-string {:hola "Mundo" :id id})}))

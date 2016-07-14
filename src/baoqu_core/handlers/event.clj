(ns baoqu-core.handlers.event
  (:require [baoqu-core.http-utils :refer [json]]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

(defn show
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/show > id=" id)
    (if (not event)
      (json 404)
      (->> event
           (event-manager-service/show-event)
           (json 200)))))

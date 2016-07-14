(ns baoqu-core.handlers.event
  (:require [baoqu-core.http-utils :refer [json]]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.user :as user-service]
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

(defn add-user
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)
        name (get-in ctx [:data :name])
        user (user-service/get-by-name name)]
    (println "[HNDLR] event/add-user > event-id=" id " user-name=" name)
    (if (not event)
      (json 404))
    (if user
      (let [already-in-event (event-service/is-user-in-event? user event)]
        (if-not already-in-event
          (as-> user x
            (event-manager-service/add-user-to-event x event)
            (json 200 x))
          (json 200 user)))
      (as-> name x
        (user-service/create x)
        (event-manager-service/add-user-to-event x event)
        (json 200 x)))))

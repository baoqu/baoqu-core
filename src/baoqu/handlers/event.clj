(ns baoqu.handlers.event
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.event :as event-service]
            [baoqu.services.user :as user-service]
            [baoqu.services.circle :as circle-service]
            [baoqu.services.idea :as idea-service]
            [baoqu.services.comment :as comment-service]
            [baoqu.services.event-manager :as event-manager-service]))

(defn list
  [ctx]
  (println "[HNDLR] event/list")
  (let [event-list (event-service/get-all)]
    (if-not event-list
      (json 404)
      (json 200 event-list))))

(defn show
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/show > id=" id)
    (if (not event)
      (json 404)
      (json 200 event))))

(defn users
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/users > id=" id)
    (if-not event
      (json 404)
      (do (println "EVENT" event)
        (->> event
           (user-service/get-all-for-event)
           (json 200))))))

(defn circles
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/circles > id=" id)
    (if-not event
      (json 404)
      (->> event
           (circle-service/get-all-for-event)
           (json 200)))))

(defn ideas
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/ideas > id=" id)
    (if-not event
      (json 404)
      (->> event
           (idea-service/get-all-for-event)
           (json 200)))))

(defn comments
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/comments > id=" id)
    (if-not event
      (json 404)
      (->> event
           (comment-service/get-all-for-event)
           (json 200)))))

(defn votes
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (event-service/get-by-id id)]
    (println "[HNDLR] event/votes > id=" id)
    (if-not event
      (json 404)
      (->> event
           (idea-service/get-all-votes-for-event)
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

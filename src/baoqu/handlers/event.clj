(ns baoqu.handlers.event
  (:refer-clojure :exclude [list])
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.event :as es]
            [baoqu.services.user :as us]
            [baoqu.services.circle :as cs]
            [baoqu.services.idea :as is]
            [baoqu.services.comment :as coms]
            [baoqu.services.event-manager :as ems]))

(defn list
  [ctx]
  (println "[HNDLR] event/list")
  (let [event-list (es/get-all)]
    (json 200 event-list)))

(defn show
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/show > id=" id)
    (if (not event)
      (json 404)
      (json 200 event))))

(defn users
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/users > id=" id)
    (if-not event
      (json 404)
      (do (println "EVENT" event)
        (->> event
           (us/get-all-for-event)
           (json 200))))))

(defn circles
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/circles > id=" id)
    (if-not event
      (json 404)
      (->> event
           (cs/get-all-for-event)
           (json 200)))))

(defn ideas
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/ideas > id=" id)
    (if-not event
      (json 404)
      (->> event
           (is/get-all-for-event)
           (json 200)))))

(defn comments
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/comments > id=" id)
    (if-not event
      (json 404)
      (->> event
           (coms/get-all-for-event)
           (json 200)))))

(defn votes
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)]
    (println "[HNDLR] event/votes > id=" id)
    (if-not event
      (json 404)
      (->> event
           (is/get-all-votes-for-event)
           (json 200)))))

(defn add-user
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        event (es/get-by-id id)
        name (get-in ctx [:data :name])
        user (us/get-by-username name)]
    (println "[HNDLR] event/add-user > event-id=" id " user-name=" name)
    (if (not event)
      (json 404))
    (if user
      (let [already-in-event (es/is-user-in-event? user event)]
        (if-not already-in-event
          (as-> user x
            (ems/add-user-to-event x event)
            (json 200 x))
          (json 200 user)))
      (as-> name x
        (us/create x)
        (ems/add-user-to-event x event)
        (json 200 x)))))

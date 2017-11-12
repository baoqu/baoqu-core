(ns baoqu.handlers.circle
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.comment-manager :as cms]
            [baoqu.services.circle :as cs]
            [baoqu.services.comment :as coms]
            [baoqu.services.user :as us]))

(defn user-circle
  [ctx]
  (let [event-id (get-in ctx [:route-params :id])
        user (us/get-user-from-ctx ctx)]
    (json 200 (cs/get-highest-level-circle user event-id))))

(defn ideas
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (cs/get-by-id id)
        user-id (get-in ctx [:query-params :user-id])
        user (us/get-by-id user-id)]
    (json 200 (cs/get-circle-ideas-for-user circle user))))

(defn comments
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (cs/get-by-id id)]
    (json 200 (coms/get-all-for-circle circle))))

(defn add-comment
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (cs/get-by-id id)
        name (get-in ctx [:data :name])
        user (us/get-by-username name)
        comment-body (get-in ctx [:data :comment-body])]
    (println "[HNDLR] circle/add-comment > id=" id)
    (if (not circle)
      (json 404))
    (if (and user comment-body)
      (->> comment-body
           (cms/create user circle)
           (json 200))
      (json 400 {:message "Invalid data"}))))

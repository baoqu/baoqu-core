(ns baoqu.handlers.circle
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.comment-manager :as comment-manager-service]
            [baoqu.services.circle :as circle-service]
            [baoqu.services.comment :as comment-service]
            [baoqu.services.user :as user-service]))

(defn user-circle
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (user-service/get-by-id id)]
    (json 200 (circle-service/get-highest-level-circle user))))

(defn ideas
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (circle-service/get-by-id id)
        user-id (get-in ctx [:query-params :user-id])
        user (user-service/get-by-id user-id)]
    (json 200 (circle-service/get-circle-ideas-for-user circle user))))

(defn comments
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (circle-service/get-by-id id)]
    (json 200 (comment-service/get-all-for-circle circle))))

(defn add-comment
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (circle-service/get-by-id id)
        name (get-in ctx [:data :name])
        user (user-service/get-by-name name)
        comment-body (get-in ctx [:data :comment-body])]
    (println "[HNDLR] circle/add-comment > id=" id)
    (if (not circle)
      (json 404))
    (if (and user comment-body)
      (->> comment-body
           (comment-manager-service/create user circle)
           (json 200))
      (json 400 {:message "Invalid data"}))))

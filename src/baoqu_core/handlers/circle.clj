(ns baoqu-core.handlers.circle
  (:require [baoqu-core.http-utils :refer [json]]
            [baoqu-core.services.comment-manager :as comment-manager-service]
            [baoqu-core.services.circle :as circle-service]
            [baoqu-core.services.comment :as comment-service]
            [baoqu-core.services.user :as user-service]))

(defn user-circle
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (user-service/get-by-id id)]
    (json 200 (circle-service/get-highest-level-circle user))))

(defn ideas
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        circle (circle-service/get-by-id id)]
    (json 200 (circle-service/get-circle-ideas circle))))

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

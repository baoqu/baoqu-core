(ns baoqu.handlers.idea
  (:require [baoqu.services.user :as user-service]
            [baoqu.services.idea :as idea-service]
            [baoqu.services.event-manager :as event-manager-service]
            [baoqu.http-utils :refer [json]]))

(defn upvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (user-service/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])]
    (if (not user)
      (json 404)
      (json 200 (event-manager-service/upvote user idea-name)))))

(defn downvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (user-service/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])
        idea (idea-service/get-by-name idea-name)]
    (if (or (not user) (not idea))
      (json 404)
      (json 200 (event-manager-service/downvote user idea)))))

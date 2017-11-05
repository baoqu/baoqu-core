(ns baoqu.handlers.idea
  (:require [baoqu.services.user :as us]
            [baoqu.services.idea :as is]
            [baoqu.services.event-manager :as ems]
            [baoqu.http-utils :refer [json]]))

 (defn upvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (us/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])
        event-id (get-in ctx [:data :event-id])]
    (if (not user)
      (json 404)
      (json 200 (ems/upvote user idea-name event-id)))))

(defn downvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (us/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])
        event-id (get-in ctx [:data :event-id])
        idea (is/get-by-name-and-event idea-name event-id)]
    (if (or (not user) (not idea))
      (json 404)
      (json 200 (ems/downvote user idea)))))

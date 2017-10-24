(ns baoqu.handlers.idea
  (:require [baoqu.services.user :as us]
            [baoqu.services.idea :as is]
            [baoqu.services.event-manager :as ems]
            [baoqu.http-utils :refer [json]]))

 (defn upvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (us/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])]
    (if (not user)
      (json 404)
      (json 200 (ems/upvote user idea-name)))))

(defn downvote
  [ctx]
  (let [user-id (get-in ctx [:data :user-id])
        user (us/get-by-id user-id)
        idea-name (get-in ctx [:data :idea-name])
        idea (is/get-by-name idea-name)]
    (if (or (not user) (not idea))
      (json 404)
      (json 200 (ems/downvote user idea)))))

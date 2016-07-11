(ns baoqu-core.services.event-manager
  (:require [baoqu-core.services.idea :as idea-service]
            [baoqu-core.services.circle :as circle-service]))

(defn add-user-to-event [user event]
  (let [incomplete-circle (circle-service/find-or-create-incomplete-circle-for-event event)]
    (circle-service/add-user-to-circle user incomplete-circle)))

(defn upvote [user idea-name]
  (let [idea (idea-service/find-or-create-idea-by-name idea-name)
        circle (user-service/get-highest-level-circle user)]
    (idea-service/upvote-idea user idea)

    ;; check if circle should grow

    ))


(defn downvote [user idea]
  (idea-service/downvote-idea user idea)

  ;; check if circle should shrink

  )

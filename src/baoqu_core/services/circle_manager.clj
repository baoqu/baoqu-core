(ns baoqu-core.services.circle-manager
  (:require [baoqu-core.services.idea :as idea-service]))

(defn upvote [user idea-name]
  (let [idea (idea-service/find-or-create-idea-by-name idea-name)]
    (idea-service/upvote-idea user idea))

  ;; check if circle should grow

  )


(defn downvote [user idea]
  (idea-service/downvote-idea user idea)

  ;; check if circle should shrink

  )

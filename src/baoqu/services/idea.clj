(ns baoqu.services.idea
  (:require [baoqu.repos.idea :as idea-repo]))

(defn get-by-id
  [id]
  (idea-repo/get-by-id id))

(defn get-by-name
  [name]
  (idea-repo/get-by-name name))

(defn create
  [name]
  (idea-repo/create name))

(defn find-or-create-idea-by-name
  [name]
  (let [idea (get-by-name name)]
    (if-not idea
      (create name)
      idea)))

(defn upvote-idea
  [user idea]
  (idea-repo/upvote-idea (:id user) (:id idea)))

(defn downvote-idea
  [user idea]
  (idea-repo/downvote-idea (:id user) (:id idea)))

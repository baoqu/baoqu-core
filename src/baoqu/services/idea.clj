(ns baoqu.services.idea
  (:require [baoqu.repos.idea :as ir]))

(defn get-by-id
  [id]
  (ir/get-by-id id))

(defn get-by-name
  [name]
  (ir/get-by-name name))

(defn get-all-for-event
  [{:keys [id]}]
  (ir/get-all-for-event id))

(defn get-all-votes-for-event
  [{:keys [id]}]
  (ir/get-all-votes-for-event id))

(defn create
  [name]
  (ir/create name))

(defn find-or-create-idea-by-name
  [name]
  (let [idea (get-by-name name)]
    (if-not idea
      (create name)
      idea)))

(defn upvote-idea
  [user idea]
  (ir/upvote-idea (:id user) (:id idea)))

(defn downvote-idea
  [user idea]
  (ir/downvote-idea (:id user) (:id idea)))

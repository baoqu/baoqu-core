(ns baoqu.services.idea
  (:require [baoqu.repos.idea :as ir]))

(defn get-by-id
  [id]
  (ir/get-by-id id))

(defn get-by-name-and-event
  [name event-id]
  (ir/get-by-name-and-event name event-id))

(defn get-all-for-event
  [{:keys [id]}]
  (ir/get-all-for-event id))

(defn get-all-votes-for-event
  [{:keys [id]}]
  (ir/get-all-votes-for-event id))

(defn create
  [name event-id]
  (ir/create name event-id))

(defn find-or-create-idea-by-name-and-event
  [name event-id]
  (let [idea (get-by-name-and-event name event-id)]
    (if-not idea
      (create name event-id)
      idea)))

(defn upvote-idea
  [user idea]
  (ir/upvote-idea (:id user) (:id idea)))

(defn downvote-idea
  [user idea]
  (ir/downvote-idea (:id user) (:id idea)))

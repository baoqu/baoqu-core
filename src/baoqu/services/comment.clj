(ns baoqu.services.comment
  (:require [baoqu.repos.comment :as comment-repo]))

(defn create
  [user circle body]
  (comment-repo/create (:id user) (:id circle) body))

(defn get-by-id
  [id]
  (comment-repo/get-by-id id))

(defn get-all
  []
  (comment-repo/get-all))

(defn get-all-for-circle
  [{:keys [id] :as circle}]
  (comment-repo/get-all-for-circle id))

(defn get-all-for-event
  [{:keys [id] :as event}]
  (comment-repo/get-all-for-event id))

(ns baoqu-core.services.comment
  (:require [baoqu-core.repos.comment :as comment-repo]))

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
  [circle]
  (comment-repo/get-all-for-circle (:id circle)))

(ns baoqu.services.user
  (:require [baoqu.repos.user :as user-repo]))

(defn create
  [name]
  (user-repo/create name))

(defn get-by-id
  [id]
  (user-repo/get-by-id id))

(defn get-by-name
  [id]
  (user-repo/get-by-name id))

(defn get-all
  []
  (user-repo/get-all))

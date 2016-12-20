(ns baoqu.services.user
  (:require [baoqu.repos.user :as user-repo]
            [baoqu.repos.circle :as circle-repo]))

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

(defn get-user-path
  [{:keys [id] :as user}]
  (->> id
       (circle-repo/get-all-for-user)
       (sort-by :level)
       (reverse)
       (map :level)
       (into [])))

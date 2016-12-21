(ns baoqu.services.user
  (:require [baoqu.repos.user :as user-repo]
            [baoqu.repos.idea :as idea-repo]
            [baoqu.repos.circle :as circle-repo]))

(defn create
  [name]
  (user-repo/create name))

(defn hydrate-with-circles
  [user]
  (->> user
       (:id)
       (circle-repo/get-all-for-user)
       (map :id)
       (into #{})
       (assoc user :circles)))

(defn hydrate-with-ideas
  [user]
  (->> user
       (:id)
       (idea-repo/get-all-for-user)
       (map :id)
       (into #{})
       (assoc user :ideas)))

(defn hydrate
  [user]
  (-> user
      (hydrate-with-circles)
      (hydrate-with-ideas)))

(defn hydrate-all
  [users]
  (into [] (map #(hydrate %) users)))

(defn get-by-id
  [id]
  (let [user (user-repo/get-by-id id)]
    (if user
      (hydrate user))))

(defn get-by-name
  [name]
  (let [user (user-repo/get-by-name name)]
    (if user
      (hydrate user))))

(defn get-all
  []
  (->> (user-repo/get-all)
       (hydrate-all)))

(defn get-all-for-event
  [{:keys [id]}]
  ;; TODO users are not related to events
  ;; (user-repo/get-all-by-event id)
  (-> (user-repo/get-all)
      (hydrate-all)))

(defn get-user-path
  [{:keys [id]}]
  (->> id
       (circle-repo/get-all-for-user)
       (sort-by :level)
       (reverse)
       (map :id)
       (into [])))

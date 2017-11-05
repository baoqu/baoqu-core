(ns baoqu.services.user
  (:require [baoqu.repos.user :as ur]
            [baoqu.repos.idea :as ir]
            [baoqu.repos.circle :as cr]
            [baoqu.services.auth :as as]))

(defn create
  [username password]
  (let [password-hash (as/encrypt password)]
    (ur/create username password-hash)))

(defn hydrate-with-circles
  [user]
  (->> user
       (:id)
       (cr/get-all-for-user)
       (map :id)
       (into #{})
       (assoc user :circles)))

(defn hydrate-with-ideas
  [user]
  (->> user
       (:id)
       (ir/get-all-for-user)
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
  (let [user (ur/get-by-id id)]
    (if user
      (hydrate user))))

(defn get-by-username
  [username]
  (let [user (ur/get-by-username username)]
    (if user
      (hydrate user))))

(defn get-all
  []
  (->> (ur/get-all)
       (hydrate-all)))

(defn get-all-for-event
  [{:keys [id]}]
  ;; TODO users are not related to events
  ;; (ur/get-all-by-event id)
  (-> (ur/get-all)
      (hydrate-all)))

(defn get-user-path
  [{:keys [id]}]
  (->> id
       (cr/get-all-for-user)
       (sort-by :level)
       (reverse)
       (map :id)
       (into [])))

(defn authenticate
  [username password]
  (if-let [user (get-by-username username)]
    (if (as/check password (:password user))
      user
      nil)
    nil))

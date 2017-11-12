(ns baoqu.repos.circle
  (:require [hugsql.core :as hugsql]
            [baoqu.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/circles.sql")

(defn create-table
  []
  (q-create-circles-table db)
  (q-create-users-circles-table db))

(defn get-by-id
  [id]
  (q-get-by-id db {:id id}))

(defn get-all
  []
  (q-get-all db))

(defn hydrate-circle-with-inner
  [circle]
  (->> {:circle-id (:id circle)}
       (q-get-inner-circles db)
       (map :id)
       (into [])
       (assoc circle :inner-circles)))

(defn get-all-for-event
  [event-id]
  (->> {:event-id event-id}
       (q-get-all-for-event db)
       (map hydrate-circle-with-inner)))

(defn get-all-for-user
  [user-id]
  (q-get-all-for-user db {:user-id user-id}))

(defn create
  [event-id level size parent-circle-id]
  (let [data {:event-id event-id :level level :size size :parent-circle-id parent-circle-id}
        res (q-insert-circle db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn persist
  [circle]
  (q-persist-circle db circle))

(defn delete
  [circle]
  (let [data {:id (:id circle)}]
    (q-delete-circle db data)))

(defn get-circle-users
  [circle]
  (let [data {:id (:id circle)}]
    (q-get-circle-users db data)))

(defn add-user-to-circle
  [user-id circle-id]
  (let [data {:user-id user-id :circle-id circle-id}]
    (q-add-user-to-circle db data)))

(defn remove-user-from-circle
  [user-id circle-id]
  (let [data {:user-id user-id :circle-id circle-id}]
    (q-remove-user-from-circle db data)))

(defn get-all-incomplete-by-event-and-level
  [event-id level agreement-factor]
  (let [data {:event-id event-id :level level :agreement-factor agreement-factor}]
    (q-get-all-incomplete db data)))

(defn get-circle-for-user-and-level
  [user-id level event-id]
  (let [data {:user-id user-id :level level :event-id event-id}]
    (q-get-circle-for-user-and-level db data)))

(defn get-highest-level-circle
  [user-id event-id]
  (q-get-highest-level-circle db {:user-id user-id :event-id event-id}))

(defn get-circle-ideas
  [circle-id]
  (q-get-circle-ideas db {:circle-id circle-id}))

(defn get-circle-agreements
  [circle-id agreement-factor event-id]
  (let [data {:agreement-factor agreement-factor :circle-id circle-id :event-id event-id}]
    (q-get-circle-agreements db data)))

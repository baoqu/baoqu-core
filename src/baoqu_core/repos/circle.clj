(ns baoqu-core.repos.circle
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

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

(defn get-all-for-event
  [event-id]
  (q-get-all-for-event db {:event-id event-id}))

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
  [user-id level]
  (let [data {:user-id user-id :level level}]
    (q-get-circle-for-user-and-level db data)))

(defn get-highest-level-circle
  [user-id]
  (q-get-highest-level-circle db {:user-id user-id}))

(defn get-circle-ideas
  [circle-id]
  (q-get-circle-ideas db {:circle-id circle-id}))

(defn get-circle-agreements
  [circle-id agreement-factor]
  (let [data {:agreement-factor agreement-factor :circle-id circle-id}]
    (q-get-circle-agreements db data)))

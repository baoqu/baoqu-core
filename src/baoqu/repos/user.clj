(ns baoqu.repos.user
  (:require [hugsql.core :as hugsql]
            [baoqu.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/users.sql")

(defn get-by-id
  [id]
  (q-get-by-id db {:id id}))

(defn get-by-name
  [name]
  (q-get-by-name db {:name name}))

(defn get-all-by-circle
  [circle-id]
  (q-get-all-by-circle db {:circle-id circle-id}))

(defn get-all
  []
  (q-get-all db))

(defn create-table
  []
  (q-create-users-table db))

(defn create
  [name]
  (let [data {:name name}
        res (q-insert-user db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

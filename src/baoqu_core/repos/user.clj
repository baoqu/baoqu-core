(ns baoqu-core.repos.user
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/users.sql")

(defn get-by-id
  [id]
  (q-get-by-id db {:id id}))

(defn create-table
  []
  (q-create-users-table db))

(defn create
  [name]
  (let [data {:name name}
        res (q-insert-user db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

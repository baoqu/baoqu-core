(ns baoqu-core.repos.comment
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/comments.sql")

(defn create-table
  []
  (q-create-comments-table db))

(defn get-by-id
  [id]
  (q-get-by-id db {:id id}))

(defn get-all
  []
  (q-get-all db))

(defn get-all-for-circle
  [circle-id]
  (q-get-all-for-circle db {:circle-id circle-id}))

(defn create
  [user-id circle-id body]
  (let [date (str (.getTime (java.util.Date.)))
        data {:user-id user-id :circle-id circle-id :body body :date date}
        res (q-insert-comment db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn persist
  [comment]
  (q-persist-comment db comment))

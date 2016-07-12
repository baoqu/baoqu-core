(ns baoqu-core.repos.event
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/events.sql")

(defn create-table
  []
  (q-create-events-table db))

(defn get-by-id
  [id]
  (q-get-by-id db {:id id}))

(defn create
  [name circle-size agreement-factor]
  (let [data {:name name :circle-size circle-size :agreement-factor agreement-factor}
        res (q-insert-event db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn all
  []) ;; filters?

(defn save
  [event])

(defn delete-by-id
  [id])

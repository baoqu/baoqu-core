(ns baoqu-core.repos.circle
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/circles.sql")

(defn create-table []
  (q-create-circles-table db)
  (q-create-users-circles-table db))

(defn get-by-id [id]
  (q-get-by-id db {:id id}))

(defn get-all []
  (q-get-all db))

(defn create [event-id level parent-circle]
  (let [data {:event-id event-id :level level :parent-circle parent-circle}
        res (q-insert-circle db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn add-user-to-circle [user-id circle-id]
  (let [data {:user-id user-id :circle-id circle-id}]
    (q-add-user-to-repo db data)))

(defn get-all-incomplete-by-event [event-id event-circle-size]
  (q-get-all-incomplete db {:event-id event-id :circle-size event-circle-size}))

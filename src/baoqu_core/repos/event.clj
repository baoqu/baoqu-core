(ns baoqu-core.repos.event
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(hugsql/def-db-fns "sql/events.sql")

(defn create-table []
  (create-events-table db))

(defn create [& props])

(defn get-by-id [id])

(defn all []) ;; filters?

(defn save [event])

(defn delete-by-id [id])

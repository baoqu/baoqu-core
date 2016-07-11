(ns baoqu-core.repos.circle
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(hugsql/def-db-fns "sql/circles.sql")

(defn create-table []
  (create-circles-table db)
  (create-users-circles-table db))

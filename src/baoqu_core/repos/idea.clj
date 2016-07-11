(ns baoqu-core.repos.idea
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(hugsql/def-db-fns "sql/ideas.sql")

(defn create-table []
  (create-ideas-table db)
  (create-users-ideas-table db))

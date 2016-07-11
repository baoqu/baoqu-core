(ns baoqu-core.repos.user
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(hugsql/def-db-fns "sql/users.sql")

(defn create-table []
  (create-users-table db))

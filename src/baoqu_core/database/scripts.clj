(ns baoqu-core.database.scripts
  (:require [clojure.java.io :refer [as-file]]
            [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db-path]]
            [baoqu-core.repos.event :as event-repo]
            [baoqu-core.repos.circle :as circle-repo]
            [baoqu-core.repos.user :as user-repo]
            [baoqu-core.repos.idea :as idea-repo]
            [baoqu-core.database.fixtures :as fixtures]))

(defn create
  []
  (println ">> Creating database")
  (user-repo/create-table)
  (event-repo/create-table)
  (circle-repo/create-table)
  (idea-repo/create-table))

(defn safely-create
  []
  (if-not (-> db-path
              (as-file)
              (.exists))
    (create)))

(defn delete
  []
  (println ">> Deleting database")
  (-> db-path
      (as-file)
      (.delete)))

(defn load-fixtures
  []
  (println ">> Loading database fixtures")
  (fixtures/load-all))

(defn reload []
  (if (-> db-path
          (as-file)
          (.exists))
    (delete))
  (create)
  (load-fixtures))

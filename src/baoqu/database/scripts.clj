(ns baoqu.database.scripts
  (:require [clojure.java.io :refer [as-file]]
            [hugsql.core :as hugsql]
            [baoqu.configuration :refer [config]]
            [baoqu.repos.event :as event-repo]
            [baoqu.repos.circle :as circle-repo]
            [baoqu.repos.user :as user-repo]
            [baoqu.repos.idea :as idea-repo]
            [baoqu.repos.comment :as comment-repo]
            [baoqu.database.fixtures :as fixtures]))

(defn create
  []
  (println ">> Creating database")
  (user-repo/create-table)
  (event-repo/create-table)
  (circle-repo/create-table)
  (idea-repo/create-table)
  (comment-repo/create-table))

(defn safely-create
  []
  (if-not (-> (:db-path config)
              (as-file)
              (.exists))
    (create)))

(defn delete
  []
  (println ">> Deleting database")
  (-> (:db-path config)
      (as-file)
      (.delete)))

(defn load-fixtures
  []
  (println ">> Loading database fixtures")
  (fixtures/load-initial-status))

(defn reload []
  (if (-> (:db-path config)
          (as-file)
          (.exists))
    (delete))
  (create)
  (load-fixtures))

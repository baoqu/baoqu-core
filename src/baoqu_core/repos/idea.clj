(ns baoqu-core.repos.idea
  (:require [hugsql.core :as hugsql]
            [baoqu-core.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/ideas.sql")

(defn create-table []
  (q-create-ideas-table db)
  (q-create-users-ideas-table db))

(defn get-by-id [id]
  (let [raw-idea (q-get-by-id db {:id id})
        votes (first (vals (q-get-idea-votes db {:id id})))]
    (assoc raw-idea :votes votes)))

(defn get-by-name [name]
  (q-get-by-name db {:name name}))

(defn create [name]
  (let [data {:name name}
        res (q-insert-idea db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn is-upvoted? [user-id idea-id]
  (not (empty? (q-get-vote db {:user-id user-id :idea-id idea-id}))))

(defn upvote-idea [user-id idea-id]
  (if-not (is-upvoted? user-id idea-id)
    (let [data {:user-id user-id :idea-id idea-id}
          res (q-upvote db data)
          new-id (get-id-from-insert res)]
      (get-by-id new-id)))
  (get-by-id idea-id))

(defn downvote-idea [user-id idea-id]
  (if (is-upvoted? user-id idea-id)
    (q-downvote db {:user-id user-id :idea-id idea-id}))
  (get-by-id idea-id))

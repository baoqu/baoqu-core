(ns baoqu.repos.idea
  (:require [hugsql.core :as hugsql]
            [baoqu.database :refer [db]]))

(def ^:const get-id-from-insert (keyword "last_insert_rowid()"))

(hugsql/def-db-fns "sql/ideas.sql")

(defn create-table
  []
  (q-create-ideas-table db)
  (q-create-users-ideas-table db))

(defn hydrate
  [idea]
  (let [votes (first (vals (q-get-idea-votes db idea)))]
    (assoc idea :votes votes)))

(defn get-by-id
  [id]
  (let [idea (q-get-by-id db {:id id})]
    (if idea (hydrate idea) idea)))

(defn get-by-name-and-event
  [name event-id]
  (let [data {:name name :event-id event-id}
        idea (q-get-by-name-and-event db data)]
    (if idea (hydrate idea) idea)))

(defn get-all-for-event
  [id]
  (q-get-all-for-event db {:event-id id}))

(defn get-all-votes-for-event
  [id]
  (q-get-all-votes-for-event db {:event-id id}))

(defn get-all-for-user
  [user-id]
  (q-get-all-for-user db {:user-id user-id}))

(defn create
  [name event-id]
  (let [data {:name name :event-id event-id}
        res (q-insert-idea db data)
        new-id (get-id-from-insert res)]
    (get-by-id new-id)))

(defn is-upvoted?
  [user-id idea-id]
  (not (empty? (q-get-vote db {:user-id user-id :idea-id idea-id}))))

(defn upvote-idea
  [user-id idea-id]
  (if-not (is-upvoted? user-id idea-id)
    (let [data {:user-id user-id :idea-id idea-id}
          res (q-upvote db data)
          new-id (get-id-from-insert res)]
      (get-by-id new-id)))
  (get-by-id idea-id))

(defn downvote-idea
  [user-id idea-id]
  (if (is-upvoted? user-id idea-id)
    (q-downvote db {:user-id user-id :idea-id idea-id}))
  (get-by-id idea-id))

(defn get-user-votes
  [user-id]
  (q-get-user-votes db {:user-id user-id}))

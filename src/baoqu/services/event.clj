(ns baoqu.services.event
  (:require [baoqu.repos.event :as event-repo]))

(defn create
  [name circle-size agreement-factor]
  (event-repo/create name circle-size agreement-factor))

(defn get-by-id
  [id]
  (event-repo/get-by-id id))

(defn is-user-in-event?
  [user event]
  (as-> user x
    (:id x)
    (event-repo/get-events-for-user x)
    (into #{} (map :id x))
    (contains? x (:id event))))

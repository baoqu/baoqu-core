(ns baoqu.services.event
  (:require [baoqu.repos.event :as er]
            [baoqu.repos.idea :as ir]))

(defn create
  [name description circle-size agreement-factor]
  (er/create name description circle-size agreement-factor))

+;(defn hydrate
+;  [event]
+;  (let [idea-count]))

(defn get-all
  []
  (er/get-all))

(defn get-by-id
  [id]
  (er/get-by-id id))

(defn is-user-in-event?
  [user event]
  (as-> user x
    (:id x)
    (er/get-events-for-user x)
    (into #{} (map :id x))
    (contains? x (:id event))))

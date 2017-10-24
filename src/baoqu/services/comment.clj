(ns baoqu.services.comment
  (:require [baoqu.repos.comment :as cr]))

(defn create
  [user circle body]
  (cr/create (:id user) (:id circle) body))

(defn get-by-id
  [id]
  (cr/get-by-id id))

(defn get-all
  []
  (cr/get-all))

(defn get-all-for-circle
  [{:keys [id] :as circle}]
  (cr/get-all-for-circle id))

(defn get-all-for-event
  [{:keys [id] :as event}]
  (cr/get-all-for-event id))

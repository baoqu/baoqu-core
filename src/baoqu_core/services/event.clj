(ns baoqu-core.services.event
  (:require [baoqu-core.repos.event :as event-repo]))

(defn create [name circle-size agreement-factor]
  (event-repo/create name circle-size agreement-factor))

(defn get-by-id [id]
  (event-repo/get-by-id id))

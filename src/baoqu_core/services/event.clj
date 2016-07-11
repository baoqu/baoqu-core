(ns baoqu-core.services.event
  (:require [baoqu-core.repos.event :as event-repo]))

(defn create [name circle-size approval-factor]
  (event-repo/create name circle-size approval-factor))

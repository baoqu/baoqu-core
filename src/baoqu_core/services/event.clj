(ns baoqu-core.services.event
  (:require [baoqu-core.repos.event :as event-repo]))

(defn create [name circle-size approval-factor]
  (event-repo/create name circle-size approval-factor))

(defn add-user [user event]
  ;; check if circle with room
  ;; if true, add to that circle
  ;; if not, create circle and add to that circle
  )

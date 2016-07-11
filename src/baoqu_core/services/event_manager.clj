(ns baoqu-core.services.event-manager
  (:require [baoqu-core.services.circle :as circle-service]))

(defn add-user-to-event [user event]
  (let [incomplete-circle (circle-service/find-or-create-incomplete-circle-for-event event)]
    (circle-service/add-user-to-circle user incomplete-circle)))

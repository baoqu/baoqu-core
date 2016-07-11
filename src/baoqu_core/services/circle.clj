(ns baoqu-core.services.circle
  (:require [baoqu-core.repos.circle :as circle-repo]))

(defn create [event-id level parent-circle]
  (circle-repo/create event-id level parent-circle))

(defn find-or-create-incomplete-circle-for-event [event]
  (let [event-id (:id event)
        event-circle-size (:circle_size event)
        incomplete-event-circles (circle-repo/get-all-incomplete-by-event event-id event-circle-size)]
    (if (empty? incomplete-event-circles)
      (circle-repo/create event-id 1 nil)
      (first incomplete-event-circles))))

(defn add-user-to-circle [user circle]
  (circle-repo/add-user-to-circle (:id user) (:id circle)))

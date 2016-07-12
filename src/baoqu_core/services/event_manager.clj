(ns baoqu-core.services.event-manager
  (:require [baoqu-core.services.idea :as idea-service]
            [baoqu-core.services.circle :as circle-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.utils :as utils]))

(defn add-user-to-event
  [user event]
  (let [agreement-factor (:agreement-factor event)
        incomplete-circle (circle-service/find-or-create-incomplete-circle-for-event-and-level event 1 agreement-factor)]
    (circle-service/add-user-to-circle user incomplete-circle)))

(defn grow-circle
  [circle]
  (let [event (event-service/get-by-id (:event-id circle))
        next-level (+ 1 (:level circle))
        circle-size (:circle-size event)
        agreement-factor (:agreement-factor event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor circle-size next-level agreement-factor)
        next-circle (circle-service/find-or-create-incomplete-circle-for-event-and-level event next-level leveled-agreement-factor)]
    (circle-service/become-child circle next-circle)))

(defn should-grow?
  [circle]
  (let [event (event-service/get-by-id (:event-id circle))
        agreement-factor (:agreement-factor event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor (:size circle) (:level circle) agreement-factor)
        agreements (circle-service/get-circle-agreements circle leveled-agreement-factor)]
    (if-not (empty? agreements)
      (grow-circle circle))))

(defn shrink-circle
  [circle])

(defn upvote
  [user idea-name]
  (let [idea (idea-service/find-or-create-idea-by-name idea-name)
        circle (circle-service/get-highest-level-circle user)]
    (idea-service/upvote-idea user idea)
    (if (should-grow? circle)
      (grow-circle circle))))


(defn downvote
  [user idea]
  (idea-service/downvote-idea user idea)
  (let [circle (circle-service/get-highest-agreed-circle user)
        agreements (circle-service/get-circle-agreements circle (:size circle))]
    (if (empty? agreements)
      (shrink-circle circle))))

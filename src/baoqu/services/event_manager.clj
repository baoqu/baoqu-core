(ns baoqu.services.event-manager
  (:require [baoqu.services.idea :as idea-service]
            [baoqu.services.circle :as circle-service]
            [baoqu.services.event :as event-service]
            [baoqu.utils :as utils]
            [baoqu.async :refer [send-sse]]))

(defn add-user-to-event
  [user event]
  (let [agreement-factor (:agreement-factor event)
        incomplete-circle (circle-service/find-or-create-incomplete-circle-for-event-and-level event 1 agreement-factor)]
    (circle-service/add-user-to-circle user incomplete-circle)
    (send-sse {} "new-user")
    user))

(defn grow-circle
  [circle]
  (let [event (event-service/get-by-id (:event-id circle))
        circle-id (:id circle)
        next-level (+ 1 (:level circle))
        circle-size (:circle-size event)
        agreement-factor (:agreement-factor event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor circle-size next-level agreement-factor)
        next-circle (circle-service/find-or-create-incomplete-circle-for-event-and-level event next-level leveled-agreement-factor)]
    (circle-service/become-child circle next-circle)
    (send-sse {} "grow")
    (send-sse {"circle-id" circle-id "title" "Tu círculo ha subido de nivel" "description" "Habéis llegado a los acuerdos necesarios para superar el factor de acuerdo."} "notification")
    next-circle))

(defn should-grow?
  [circle]
  (let [event (event-service/get-by-id (:event-id circle))
        agreement-factor (:agreement-factor event)
        agreements (circle-service/get-circle-agreements circle agreement-factor)]
    (not (empty? agreements))))

(defn shrink-circle-from-user
  [user]
  (let [hightest-circle (circle-service/get-highest-level-circle user)
        hightest-agreed-circle (circle-service/get-highest-agreed-circle user)]
    (circle-service/remove-child hightest-agreed-circle hightest-circle)
    (send-sse {"circle-id" (:id hightest-circle) "title" "Tu círculo ha bajado de nivel" "description" "Ninguna idea tiene apoyos suficientes para superar el factor de acuerdo."} "notification")
    (send-sse {} "shrink")
    user))

(defn should-shrink?
  [user]
  (let [circle (circle-service/get-highest-agreed-circle user)]
    (if-not (empty? circle)
      (let [agreements (circle-service/get-circle-agreements circle (:size circle))]
        (empty? agreements))
      false)))

(defn upvote
  "Finds or creates the idea and upvotes it if it wasn't. Can trigger
  recursive circle growth"
  [user idea-name]
  (let [idea (idea-service/find-or-create-idea-by-name idea-name)
        circle (circle-service/get-highest-level-circle user)]
    (idea-service/upvote-idea user idea)
    (send-sse {:idea idea :user user} "upvote")
    (loop [circle circle]
      (if (should-grow? circle)
        (recur (grow-circle circle))
        circle))))

(defn downvote
  "Downvotes an already upvoted idea. Can trigger recursive circle shrink"
  [user idea]
  (idea-service/downvote-idea user idea)
  (send-sse {:idea idea :user user} "downvote")
  (loop [user user]
    (if (should-shrink? user)
      (recur (shrink-circle-from-user user))
      user)))

(defn show-event-detail
  [event]
  (let [circles (circle-service/get-all-for-event event)]
    {:event event
     :circles circles}))

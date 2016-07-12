(ns baoqu-core.services.circle
  (:require [baoqu-core.repos.circle :as circle-repo]
            [baoqu-core.utils :as utils]))

(defn create
  [event-id level parent-circle]
  (circle-repo/create event-id level parent-circle))

(defn get-by-id
  [id]
  (circle-repo/get-by-id id))

(defn find-or-create-incomplete-circle-for-event-and-level
  [event level agreement-factor]
  (let [event-id (:id event)
        circle-size (:circle-size event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor circle-size level agreement-factor)
        incomplete-event-circles (circle-repo/get-all-incomplete-by-event-and-level event-id level leveled-agreement-factor)]
    (if (empty? incomplete-event-circles)
      (circle-repo/create event-id level circle-size nil)
      (first incomplete-event-circles))))

(defn become-child
  "The first circle becomes the child of the second, transfering its users
  and setting the adequate parameters on both sides"
  [child-circle parent-circle]

  ;; update child
  (->> parent-circle
      (:id)
      (assoc child-circle :parent-circle-id)
      (circle-repo/persist))

  ;; add child users to parent
  (as-> child-circle x
       (circle-repo/get-circle-users x)
       (map :id x)
       (doseq [user-id x]
         (circle-repo/add-user-to-circle user-id (:id parent-circle)))))

(defn add-user-to-circle
  [user circle]
  (circle-repo/add-user-to-circle (:id user) (:id circle)))

(defn get-circle-for-user-and-level
  [user level]
  (circle-repo/get-circle-for-user-and-level (:id user) level))

(defn get-highest-level-circle
  [user]
  (circle-repo/get-highest-level-circle (:id user)))

(defn get-highest-agreed-circle
  "Highest circle - 1"
  [user]
  (let [highest-circle (get-highest-level-circle user)
        highest-agreed-level (- (:level highest-circle) 1)]
    (get-circle-for-user-and-level user highest-agreed-level)))

(defn get-circle-ideas
  [circle]
  (circle-repo/get-circle-ideas (:id circle)))

(defn get-circle-agreements
  [circle agreement-factor]
  (let [size (:size circle)
        level (:level circle)
        leveled-agreement-factor (utils/get-leveled-agreement-factor size level agreement-factor)]
    (circle-repo/get-circle-agreements (:id circle) leveled-agreement-factor)))

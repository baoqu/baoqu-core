(ns baoqu-core.services.circle
  (:require [baoqu-core.repos.circle :as circle-repo]
            [baoqu-core.repos.idea :as idea-repo]
            [baoqu-core.utils :as utils]))

(defn create
  [event-id level parent-circle]
  (circle-repo/create event-id level parent-circle))

(defn get-by-id
  [id]
  (circle-repo/get-by-id id))

(defn get-all
  []
  (circle-repo/get-all))

(defn get-all-for-event
  [event]
  (circle-repo/get-all-for-event (:id event)))

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

(defn remove-child
  "The first circle is removed as a child from the second, deleting its users
  and the parent if no more users are left"
  [child-circle parent-circle]

  ;; update child
  (->> nil
   (assoc child-circle :parent-circle-id)
   (circle-repo/persist))

  ;; remove users from parent
  (as-> child-circle x
   (circle-repo/get-circle-users x)
   (map :id x)
   (doseq [user-id x]
     (circle-repo/remove-user-from-circle user-id (:id parent-circle))))

  ;; delete parent if necessary
  (if (empty? (circle-repo/get-circle-users parent-circle))
    (circle-repo/delete parent-circle)))

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
  (when-let [highest-circle (get-highest-level-circle user)]
    (->> (- (:level highest-circle) 1)
         (get-circle-for-user-and-level user))))

(defn get-circle-ideas
  [circle]
  (circle-repo/get-circle-ideas (:id circle)))

(defn- hydrate-with-user-votes
  [ideas user]
  (let [votes (idea-repo/get-user-votes (:id user))
        voted-ideas-ids (into #{} (map :idea-id votes))]
    (for [idea ideas]
               (let [voted? (contains? voted-ideas-ids (:id idea))]
                 (assoc idea "voted?" voted?)))))

(defn get-circle-ideas-for-user
  [circle user]
  (let [ideas (get-circle-ideas circle)]
    (hydrate-with-user-votes ideas user)))

(defn get-circle-agreements
  [circle agreement-factor]
  (let [size (:size circle)
        level (:level circle)
        leveled-agreement-factor (utils/get-leveled-agreement-factor size level agreement-factor)]
    (circle-repo/get-circle-agreements (:id circle) leveled-agreement-factor)))

(ns baoqu.services.circle
  (:require [baoqu.repos.circle :as cr]
            [baoqu.repos.idea :as ir]
            [baoqu.repos.user :as ur]
            [baoqu.utils :as utils]))

(defn create
  [event-id level parent-circle]
  (cr/create event-id level parent-circle))

(defn get-by-id
  [id]
  (cr/get-by-id id))

(defn hydrate-with-users
  [circles]
  (into []
          (map #(->> %
                     (:id)
                     (ur/get-all-by-circle)
                     (map :id)
                     (into #{})
                     (assoc % :users))
               circles)))

(defn get-all
  []
  (-> (cr/get-all)
      (hydrate-with-users)))

(defn get-all-for-event
  [event]
  (-> event
      (:id)
      (cr/get-all-for-event)
      (hydrate-with-users)))

(defn find-or-create-incomplete-circle-for-event-and-level
  [event level agreement-factor]
  (let [event-id (:id event)
        circle-size (:circle-size event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor circle-size level agreement-factor)
        incomplete-event-circles (cr/get-all-incomplete-by-event-and-level event-id level leveled-agreement-factor)]
    (if (empty? incomplete-event-circles)
      (cr/create event-id level circle-size nil)
      (first incomplete-event-circles))))

(defn become-child
  "The first circle becomes the child of the second, transfering its users
  and setting the adequate parameters on both sides"
  [child-circle parent-circle]

  ;; update child
  (->> parent-circle
      (:id)
      (assoc child-circle :parent-circle-id)
      (cr/persist))

  ;; add child users to parent
  (as-> child-circle x
       (cr/get-circle-users x)
       (map :id x)
       (doseq [user-id x]
         (cr/add-user-to-circle user-id (:id parent-circle)))))

(defn remove-child
  "The first circle is removed as a child from the second, deleting its users
  and the parent if no more users are left"
  [child-circle parent-circle]

  ;; update child
  (->> nil
   (assoc child-circle :parent-circle-id)
   (cr/persist))

  ;; remove users from parent
  (as-> child-circle x
   (cr/get-circle-users x)
   (map :id x)
   (doseq [user-id x]
     (cr/remove-user-from-circle user-id (:id parent-circle))))

  ;; delete parent if necessary
  (if (empty? (cr/get-circle-users parent-circle))
    (cr/delete parent-circle)))

(defn add-user-to-circle
  [user circle]
  (cr/add-user-to-circle (:id user) (:id circle)))

(defn get-circle-for-user-and-level
  [user level event-id]
  (cr/get-circle-for-user-and-level (:id user) level event-id))

(defn get-highest-level-circle
  [user event-id]
  (cr/get-highest-level-circle (:id user) event-id))

(defn get-highest-agreed-circle
  "Highest circle - 1"
  [user event-id]
  (when-let [highest-circle (get-highest-level-circle user event-id)]
    (->> (- (:level highest-circle) 1)
         (get-circle-for-user-and-level user event-id))))

(defn get-circle-ideas
  [circle]
  (cr/get-circle-ideas (:id circle)))

(defn- hydrate-with-user-votes
  [ideas user]
  (let [votes (ir/get-user-votes (:id user))
        voted-ideas-ids (into #{} (map :idea-id votes))]
    (for [idea ideas]
               (let [voted? (contains? voted-ideas-ids (:id idea))]
                 (assoc idea "voted?" voted?)))))

(defn get-circle-ideas-for-user
  [circle user]
  (let [ideas (get-circle-ideas circle)]
    (hydrate-with-user-votes ideas user)))

(defn get-circle-agreements
  [{:keys [size level event-id] :as circle} agreement-factor]
  (let [leveled-agreement-factor (utils/get-leveled-agreement-factor size level agreement-factor)]
    (cr/get-circle-agreements (:id circle) leveled-agreement-factor event-id)))

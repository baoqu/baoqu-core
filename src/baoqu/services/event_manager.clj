(ns baoqu.services.event-manager
  "This service is in charge which affect to an event as a whole. It
  orchestrates the logic and calls to the specific services involved
  in processing each request.

  This service is in charge of the logic of one of the most important
  parts of the Baoqu application: **the algorithm**. This algorithm is
  in charge of controlling what happens with the event's circles each
  time a user upvotes or downvotes an idea.

  When an idea is upvoted or downvoted, the global state of the circle
  can change if it gets to an agreement with that upvote (and then, it
  goes up one level) or if the agreement is broken (and then the
  circle goes down one level and the global state needs to be
  recalculated).

# How the Algorithm works

  Each event has two key parameters algorithm-wise:

  - **circle-size**: indicates how many users can be in a level 1
  circle at the same time, or how many circles of level n-1 fit inside
  a level n circle.

  - **agreement-factor**: indicates how many votes are neccesary for
  an idea inside a circle for its users to get to an agreement.

At the beginning of the event, we will have all users inside level 0
  circles. Let's imagine that we have an event with the following
  characteristics:

    circle-size: 3
    agreement-factor: 3
    users: 9

  When the event starts, we will have 3 circles with 3 users
  each. Whenever an idea gets to have 3 votes (the
  **agreement-factor**) on a given circle, lets say `circle A`, it
  will go up one level. A new level 2 circle will be created, which
  will contain the `circle A` and nothing more.

  If more circles get to the same or other agreements, they will be
  leveling up and joining the level 2 circle. If any of those circles
  break its internal agreement, it will go down one level and be on
  its own again. Finally, if the whole level 2 circle gets to an
  agreement, it'll level up to a level 3 circle, containing one level
  2 circle and waiting for another two."
  (:require [baoqu.services.idea :as is]
            [baoqu.services.circle :as cs]
            [baoqu.services.event :as es]
            [baoqu.utils :as utils]
            [baoqu.async :refer [send-sse]]))

(defn add-user-to-event
  "To add an user to the event, we need to check if we have any
  available circle this user could join or create one empty for
  him/her.

  After adding the user to either the empty or the half filled circle,
  we will send an event to the frontend."
  [user event]
  (let [event-id (:id event)
        agreement-factor (:agreement-factor event)
        incomplete-circle (cs/find-or-create-incomplete-circle-for-event-and-level event 1 agreement-factor)]
    (cs/add-user-to-circle user incomplete-circle)
    (send-sse {} event-id "new-user")
    user))

(declare grow-circle)
(declare should-grow?)

(defn upvote
  "When an upvote arrives, it is passed directly to the `is`
  and a new association between the user and the idea is created.

## Recursive agreement

  It might happen that the nth circle, when getting to an agreement
  and leveling up, contributes to the n+1 circle with enough votes on
  the same or other ideas for it to get to an agreement too, and level
  up again, and so on.

  To cover this case, after the growing has been completed, a
  recursive `should-grow?` call is done on the n+1 circle.

  The same thing happens when shrinking a circle."
  [user idea-name event-id]
  (let [idea (is/find-or-create-idea-by-name-and-event idea-name event-id)
        circle (cs/get-highest-level-circle user event-id)]
    (is/upvote-idea user idea)
    (send-sse {:idea idea :user user :circle-id (:id circle)} event-id "upvote")
    (loop [circle circle]
      (if (should-grow? circle)
        (recur (grow-circle circle))
        circle))))

(defn should-grow?
  "This method is called whenever an upvote happens inside a circle,
  and decides if, as a result, the circle should grow or not.

  To make this decision, it needs to get all the agreements for a
  circle given its agreement-factor and if there is any, it will call
  the `grow-circle` method."
  [circle]
  (let [event (es/get-by-id (:event-id circle))
        agreement-factor (:agreement-factor event)
        agreements (cs/get-circle-agreements circle agreement-factor)]
    (not (empty? agreements))))

(defn grow-circle
  "This method increases a circle's level. It does so by checking if
  there is any circle of level n+1 with space in it or it creates a
  new one. Then the method copies all the users to the new circle and
  sends a sse notification to the clients for them to show it in the
  ui."
  [circle]
  (let [event-id (:event-id circle)
        event (es/get-by-id event-id)
        circle-id (:id circle)
        next-level (+ 1 (:level circle))
        circle-size (:circle-size event)
        agreement-factor (:agreement-factor event)
        leveled-agreement-factor (utils/get-leveled-agreement-factor circle-size next-level agreement-factor)
        next-circle (cs/find-or-create-incomplete-circle-for-event-and-level event next-level leveled-agreement-factor)]
    (cs/become-child circle next-circle)
    (send-sse {} event-id "grow")
    (send-sse {"circle-id" circle-id "title" "Habéis alcanzado un acuerdo" "description" "Ahora os vamos a juntar con más gente que ha conseguido también un acuerdo, a ver si sois capaces de llegar a un acuerdo. ¿De acuerdo?"} event-id "notification")
    next-circle))

(declare shrink-circle-from-user)
(declare should-shrink?)

(defn downvote
  "When a downvote arrives, it's passed to the `is` to
  remove the association between the user and the idea. As a result of
  that removal, the highest circle in which the user is in can shrink
  because the agreement has been broken, so the method performs this
  check.

## Recursive shrink

  As a result of the downvote of an idea, not only the highest circle
  of the user can shrink, but if that idea was the glue of any or the
  smaller circles of the user, those should shrink too.

  Because of this, the `should-shrink?` test is done recursively, to
  cover the case of a user causing more various circles to shrink."
  [user idea event-id]
  (let [circle (cs/get-highest-level-circle user event-id)]
    (is/downvote-idea user idea)
    (send-sse {:idea idea :user user :circle-id (:id circle)} event-id "downvote")
    (loop [user user]
      (if (should-shrink? user event-id)
        (recur (shrink-circle-from-user user event-id))
        user))))

(defn should-shrink?
  "This method is called whenever a downvote happens for a circle. As
  opposed to `should-grow?`, this method receives the `user` who
  authored the downvote and not a `circle`.

  It starts obtaining the highest circle the user is in, because is
  the first one which could shrink. Then it checks for the agreements
  and if there are none left, it decides it should shrink."
  [user event-id]
  (let [circle (cs/get-highest-agreed-circle user)]
    (if-not (empty? circle)
      (let [agreements (cs/get-circle-agreements circle (:size circle))]
        (empty? agreements))
      false)))

(defn shrink-circle-from-user
  "When we've decided that a circle should shrink, we need to get the
  n+1 circle where the nth circle is right now and where it should
  stop being after the shrinking process.

  We start from the user who downvoted, which is the common point in
  between these two circles, and after getting those circles, we
  delegate in `cs` the removal of the links in between
  both circles.

  When the process is completed, we send a notification to the
  front-end and return the user to be used in the recursive
  `should-shrink?` call."
  [user event-id]
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  ;; habrá que cambiar estas funciones para que usen event-id
  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
  (let [hightest-circle (cs/get-highest-level-circle user event-id)
        hightest-agreed-circle (cs/get-highest-agreed-circle user event-id)]
    (cs/remove-child hightest-agreed-circle hightest-circle)
    ;; (send-sse {"circle-id" (:id hightest-circle) "title" "Parece que ya no estáis de acuerdo" "description" "Os vamos a separar para que intentéis encontrar de nuevo aquella idea que compartís."} "notification")
    ;; Related to the SSE message: There is no way to distinguish if
    ;; I'm shrinking or just observing how a brother shrinks
    ;; TODO: include a path in the client's state
    (send-sse {} event-id "shrink")
    user))

(defn show-event-detail
  "This method just builds a map with the event details and returns
  it to the caller."
  [event]
  (let [circles (cs/get-all-for-event event)]
    {:event event
     :circles circles}))

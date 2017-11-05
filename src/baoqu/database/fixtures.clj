(ns baoqu.database.fixtures
  (:require [baoqu.services.user :as us]
            [baoqu.services.event :as es]
            [baoqu.services.idea :as is]
            [baoqu.services.circle :as cs]
            [baoqu.services.event-manager :as ems]
            [baoqu.services.comment-manager :as cms]))

(defn load-initial-status
  []
  (es/create "Probando Baoqu" "my description" 3 3))

(defn load-all
  []
  (let [{event-id :id :as event} (es/create "Ciclismo en la Guindalera, una discusión abierta" "Ma description" 3 3)

        ;; Users
        user1 (us/create "Ramira" "secret")
        user2 (us/create "Maria" "secret")
        user3 (us/create "Yamilo" "secret")
        user4 (us/create "Mario" "secret")
        user5 (us/create "Pérez-Reverte" "secret")
        user6 (us/create "Yisus" "secret")
        user7 (us/create "Magikarp" "secret")

        idea-name1 "En la décima PIWEEK lo arreglamos todo"
        idea-name2 "Nos hacen falta más carriles bici"
        idea-name3 "Las bicis son para hippies, ¡cómo se nota que no trabajais!"
        idea-name4 "Cómo conectar con el centro: bulevar en Juan Bravo"
        idea-name5 "Sin presupuestos es difícil hacer nada"
        idea-name6 "Hay que arreglar los baches, son un peligro"]

    ;; Add users to event
    (println "++ Adding users")
    (ems/add-user-to-event user1 event)
    (ems/add-user-to-event user2 event)
    (ems/add-user-to-event user3 event)
    (ems/add-user-to-event user4 event)
    (ems/add-user-to-event user5 event)
    (ems/add-user-to-event user6 event)
    (ems/add-user-to-event user7 event)

    ;; Vote ideas
    (println "++ Voting ideas")
    (ems/upvote user1 idea-name1 event-id)
    (ems/upvote user2 idea-name1 event-id)
    (ems/upvote user3 idea-name1 event-id)
    (ems/upvote user4 idea-name1 event-id)
    (ems/upvote user5 idea-name1 event-id)
    (ems/upvote user6 idea-name1 event-id)
    (ems/upvote user7 idea-name1 event-id)

    (ems/upvote user1 idea-name2 event-id)
    (ems/upvote user2 idea-name2 event-id)

    (ems/upvote user3 idea-name3 event-id)
    (ems/upvote user4 idea-name3 event-id)

    (ems/upvote user4 idea-name4 event-id)
    (ems/upvote user5 idea-name4 event-id)

    (ems/upvote user6 idea-name5 event-id)
    (ems/upvote user7 idea-name5 event-id)

    (ems/upvote user7 idea-name6 event-id)

    ;(def idea1 (is/get-by-name-and-event idea-name1 event-id))
    ;(def idea2 (is/get-by-name-and-event idea-name2 event-id))
    ;(def idea3 (is/get-by-name-and-event idea-name3 event-id))
    ;(def idea4 (is/get-by-name-and-event idea-name4 event-id))
    ;(def idea5 (is/get-by-name-and-event idea-name5 event-id))
    (def idea6 (is/get-by-name-and-event idea-name6 event-id))

    ;; Downvote ideas
    (ems/downvote user7 idea6 event-id)

    ;; Add comments
    (def user-7-circle (cs/get-highest-level-circle user7))
    (cms/create user7 user-7-circle "Hola bobos!")

    ))

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
  (let [event (es/create "Ciclismo en la Guindalera, una discusión abierta" "Ma description" 3 3)

        ;; Users
        user1 (us/create "Ramira")
        user2 (us/create "Maria")
        user3 (us/create "Yamilo")
        user4 (us/create "Mario")
        user5 (us/create "Pérez-Reverte")
        user6 (us/create "Yisus")
        user7 (us/create "Magikarp")

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
    (ems/upvote user1 idea-name1)
    (ems/upvote user2 idea-name1)
    (ems/upvote user3 idea-name1)
    (ems/upvote user4 idea-name1)
    (ems/upvote user5 idea-name1)
    (ems/upvote user6 idea-name1)
    (ems/upvote user7 idea-name1)

    (ems/upvote user1 idea-name2)
    (ems/upvote user2 idea-name2)

    (ems/upvote user3 idea-name3)
    (ems/upvote user4 idea-name3)

    (ems/upvote user4 idea-name4)
    (ems/upvote user5 idea-name4)

    (ems/upvote user6 idea-name5)
    (ems/upvote user7 idea-name5)

    (ems/upvote user7 idea-name6)

    ;(def idea1 (is/get-by-name idea-name1))
    ;(def idea2 (is/get-by-name idea-name2))
    ;(def idea3 (is/get-by-name idea-name3))
    ;(def idea4 (is/get-by-name idea-name4))
    ;(def idea5 (is/get-by-name idea-name5))
    (def idea6 (is/get-by-name idea-name6))

    ;; Downvote ideas
    (ems/downvote user7 idea6)

    ;; Add comments
    (def user-7-circle (cs/get-highest-level-circle user7))
    (cms/create user7 user-7-circle "Hola bobos!")

    ))

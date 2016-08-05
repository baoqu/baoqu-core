(ns baoqu.database.fixtures
  (:require [baoqu.services.user :as user-service]
            [baoqu.services.event :as event-service]
            [baoqu.services.idea :as idea-service]
            [baoqu.services.circle :as circle-service]
            [baoqu.services.event-manager :as event-manager-service]
            [baoqu.services.comment-manager :as comment-manager-service]))

(defn load-all
  []
  (let [event (event-service/create "Ciclismo en la Guindalera, una discusión abierta" 3 3)

        ;; Users
        user1 (user-service/create "Ramira")
        user2 (user-service/create "Maria")
        user3 (user-service/create "Yamilo")
        user4 (user-service/create "Mario")
        user5 (user-service/create "Pérez-Reverte")
        user6 (user-service/create "Yisus")
        user7 (user-service/create "Magikarp")

        idea-name1 "En la décima PIWEEK lo arreglamos todo"
        idea-name2 "Nos hacen falta más carriles bici"
        idea-name3 "Las bicis son para hippies, ¡cómo se nota que no trabajais!"
        idea-name4 "Cómo conectar con el centro: bulevar en Juan Bravo"
        idea-name5 "Sin presupuestos es difícil hacer nada"
        idea-name6 "Hay que arreglar los baches, son un peligro"]

    ;; Add users to event
    (println "++ Adding users")
    (event-manager-service/add-user-to-event user1 event)
    (event-manager-service/add-user-to-event user2 event)
    (event-manager-service/add-user-to-event user3 event)
    (event-manager-service/add-user-to-event user4 event)
    (event-manager-service/add-user-to-event user5 event)
    (event-manager-service/add-user-to-event user6 event)
    (event-manager-service/add-user-to-event user7 event)

    ;; Vote ideas
    (println "++ Voting ideas")
    (event-manager-service/upvote user1 idea-name1)
    (event-manager-service/upvote user2 idea-name1)
    (event-manager-service/upvote user3 idea-name1)
    (event-manager-service/upvote user4 idea-name1)
    (event-manager-service/upvote user5 idea-name1)
    (event-manager-service/upvote user6 idea-name1)
    (event-manager-service/upvote user7 idea-name1)

    (event-manager-service/upvote user1 idea-name2)
    (event-manager-service/upvote user2 idea-name2)

    (event-manager-service/upvote user3 idea-name3)
    (event-manager-service/upvote user4 idea-name3)

    (event-manager-service/upvote user4 idea-name4)
    (event-manager-service/upvote user5 idea-name4)

    (event-manager-service/upvote user6 idea-name5)
    (event-manager-service/upvote user7 idea-name5)

    (event-manager-service/upvote user7 idea-name6)

    ;(def idea1 (idea-service/get-by-name idea-name1))
    ;(def idea2 (idea-service/get-by-name idea-name2))
    ;(def idea3 (idea-service/get-by-name idea-name3))
    ;(def idea4 (idea-service/get-by-name idea-name4))
    ;(def idea5 (idea-service/get-by-name idea-name5))
    (def idea6 (idea-service/get-by-name idea-name6))

    ;; Downvote ideas
    (event-manager-service/downvote user7 idea6)

    ;; Add comments
    (def user-7-circle (circle-service/get-highest-level-circle user7))
    (comment-manager-service/create user7 user-7-circle "Hola bobos!")

    ))

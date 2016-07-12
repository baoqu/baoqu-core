(ns baoqu-core.database.fixtures
  (:require [baoqu-core.services.user :as user-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.idea :as idea-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

(defn load-all
  []
  (let [event (event-service/create "Decision maker 2000" 3 3)

        ;; Users
        user1 (user-service/create "test1")
        user2 (user-service/create "test2")
        user3 (user-service/create "test3")
        user4 (user-service/create "test4")
        user5 (user-service/create "test5")
        user6 (user-service/create "test6")
        user7 (user-service/create "test7")
        user8 (user-service/create "test8")
        user9 (user-service/create "test9")
        user10 (user-service/create "test10")

        idea-name "Win the Xth PIWEEK"]

    ;; Add users to event
    (println "++ Adding users")
    (event-manager-service/add-user-to-event user1 event)
    (event-manager-service/add-user-to-event user2 event)
    (event-manager-service/add-user-to-event user3 event)
    (event-manager-service/add-user-to-event user4 event)
    (event-manager-service/add-user-to-event user5 event)
    (event-manager-service/add-user-to-event user6 event)
    (event-manager-service/add-user-to-event user7 event)
    (event-manager-service/add-user-to-event user8 event)
    (event-manager-service/add-user-to-event user9 event)
    (event-manager-service/add-user-to-event user10 event)

    ;; Vote ideas
    (println "++ Voting ideas")
    (event-manager-service/upvote user1 idea-name)
    (def idea (idea-service/get-by-name idea-name))
    (event-manager-service/upvote user2 idea-name)
    (event-manager-service/upvote user3 idea-name)
    (event-manager-service/upvote user4 idea-name)
    (event-manager-service/upvote user5 idea-name)
    (event-manager-service/upvote user6 idea-name)
    (event-manager-service/upvote user7 idea-name)
    (event-manager-service/upvote user8 idea-name)
    (event-manager-service/upvote user9 idea-name)

    ;; Downvote ideas
    (event-manager-service/downvote user1 idea)

    ))

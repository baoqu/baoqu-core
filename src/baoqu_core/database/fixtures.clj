(ns baoqu-core.database.fixtures
  (:require [baoqu-core.services.user :as user-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

(defn load-all []
  (let [event (event-service/create "Decision maker 2000" 3 3)
        user1 (user-service/create "test1")
        user2 (user-service/create "test2")
        user3 (user-service/create "test3")
        user4 (user-service/create "test4")
        user5 (user-service/create "test5")
        user6 (user-service/create "test6")
        user7 (user-service/create "test7")]
    (event-manager-service/add-user-to-event user1 event)
    (event-manager-service/add-user-to-event user2 event)
    (event-manager-service/add-user-to-event user3 event)
    (event-manager-service/add-user-to-event user4 event)
    (event-manager-service/add-user-to-event user5 event)
    (event-manager-service/add-user-to-event user6 event)
    (event-manager-service/add-user-to-event user7 event)))

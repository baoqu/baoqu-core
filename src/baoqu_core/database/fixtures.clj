(ns baoqu-core.database.fixtures
  (:require [baoqu-core.services.user :as user-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

(defn load-all []
  (let [event (event-service/create "Decision maker 2000" 3 3)
        user (user-service/create "test")]
    (event-manager-service/add-user-to-event user event)))

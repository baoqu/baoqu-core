(ns user
  (:require [clojure.tools.namespace.repl :as repl]
            [baoqu-core.repos.circle :as circle-repo]
            [baoqu-core.repos.idea :as idea-repo]
            [baoqu-core.services.circle :as circle-service]
            [baoqu-core.services.user :as user-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

(def c1 (circle-service/get-by-id 1))
(def c2 (circle-service/get-by-id 2))
(def c3 (circle-service/get-by-id 3))
(def c4 (circle-service/get-by-id 4))
(def c5 (circle-service/get-by-id 5))


(def idea "Vamos a ganar!!")

(def u1 (user-service/get-by-id 1))
(def u2 (user-service/get-by-id 2))
(def u3 (user-service/get-by-id 3))
(def u4 (user-service/get-by-id 4))
(def u5 (user-service/get-by-id 5))
(def u6 (user-service/get-by-id 6))

(def m (user-service/get-by-id 7))
(def c (circle-service/get-highest-level-circle m))

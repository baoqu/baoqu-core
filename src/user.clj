(ns user
  (:require [clojure.tools.namespace.repl :as repl]
            [baoqu-core.repos.circle :as circle-repo]
            [baoqu-core.services.circle :as circle-service]
            [baoqu-core.services.user :as user-service]
            [baoqu-core.services.event :as event-service]
            [baoqu-core.services.event-manager :as event-manager-service]))

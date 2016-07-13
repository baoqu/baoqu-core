(ns baoqu-core.core
  (:require [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [baoqu-core.handlers.root :refer [sse-handler example-handler]]
            [baoqu-core.handlers.middleware :refer [cors-handler]]
            [baoqu-core.handlers.event :as event-handlers]))

(def app
  (ct/routes [[:any #'cors-handler]
              [:any (parse/body-params)]
              [:get "sse" #'sse-handler]
              [:prefix "api"
               [:any "" #'example-handler]
               [:get "event/:id" #'event-handlers/show]]]))

(defn -main
  [& args]
  (ct/run-server app {:port 3030}))

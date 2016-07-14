(ns baoqu-core.core
  (:require [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [baoqu-core.handlers.root :refer [sse-handler example-handler]]
            [baoqu-core.handlers.middleware :refer [cors-handler]]
            [baoqu-core.handlers.event :as event-handlers]
            [baoqu-core.handlers.circle :as circle-handlers]))

(def app
  (ct/routes [[:any #'cors-handler]
              [:any (parse/body-params)]
              [:get "sse" #'sse-handler]
              [:prefix "api"
               [:any "" #'example-handler]
               [:get "user-circle/:id" #'circle-handlers/user-circle]
               [:prefix "events"
                [:get ":id" #'event-handlers/show]
                [:get ":id/circles" #'event-handlers/circles]
                [:post ":id/users" #'event-handlers/add-user]]
               [:prefix "circles"
                [:get ":id/comments" #'circle-handlers/comments]
                [:post ":id/comments" #'circle-handlers/add-comment]
                [:get ":id/ideas" #'circle-handlers/ideas]]]]))

(defn -main
  [& args]
  (println "Starting Baoqu application on port 3030")
  (ct/run-server app {:port 3030}))

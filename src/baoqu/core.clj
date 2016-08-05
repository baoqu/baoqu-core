(ns baoqu.core
  (:require [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [baoqu.configuration :refer [config]]
            [baoqu.handlers.root :refer [sse-handler example-handler]]
            [baoqu.handlers.middleware :refer [cors-handler]]
            [baoqu.handlers.event :as event-handlers]
            [baoqu.handlers.idea :as idea-handlers]
            [baoqu.handlers.circle :as circle-handlers]))

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
                [:get ":id/ideas" #'circle-handlers/ideas]]
               [:prefix "ideas"
                [:post "downvote" #'idea-handlers/downvote]
                [:post "upvote" #'idea-handlers/upvote]]]]))

(defn -main
  [& args]
  (let [server-port (:server-port config)]
    (println "Starting Baoqu application on port" server-port)
    (ct/run-server app {:port server-port})))

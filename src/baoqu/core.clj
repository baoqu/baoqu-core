(ns baoqu.core
  (:require [mount.core :as mount]
            [mount.core :refer [defstate]]
            [clojure.tools.namespace.repl :as repl]
            [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [baoqu.configuration :refer [config]]
            [baoqu.handlers.root :refer [sse-handler example-handler]]
            [baoqu.handlers.middleware :refer [cors-handler]]
            [baoqu.handlers.event :as event-handlers]
            [baoqu.handlers.user :as user-handlers]
            [baoqu.handlers.idea :as idea-handlers]
            [baoqu.handlers.circle :as circle-handlers]))

(declare -main)

(defn- start
  []
  (-main))

(defn- stop
  []
  (mount/stop))

(def app
  (ct/routes [[:any #'cors-handler]
              [:any (parse/body-params)]
              [:get "sse" #'sse-handler]
              [:prefix "api"
               [:any "" #'example-handler]
               [:prefix "users"
                [:get ":id/path" #'user-handlers/path]]
               [:get "user-circle/:id" #'circle-handlers/user-circle]
               [:prefix "events"
                [:get "" #'event-handlers/list]
                [:get ":id" #'event-handlers/show]
                [:get ":id/circles" #'event-handlers/circles]
                [:get ":id/users" #'event-handlers/users]
                [:post ":id/users" #'event-handlers/add-user]
                [:get ":id/ideas" #'event-handlers/ideas]
                [:get ":id/comments" #'event-handlers/comments]
                [:get ":id/votes" #'event-handlers/votes]]
               [:prefix "circles"
                [:get ":id/comments" #'circle-handlers/comments]
                [:post ":id/comments" #'circle-handlers/add-comment]
                [:get ":id/ideas" #'circle-handlers/ideas]]
               [:prefix "ideas"
                [:post "downvote" #'idea-handlers/downvote]
                [:post "upvote" #'idea-handlers/upvote]]]]))

(defn server-start
  []
  (let [server-port (:server-port config)]
    (ct/run-server app {:port server-port})))

(defstate server
  :start (server-start)
  :stop (.stop server))

(defn -main
  [& args]
  (mount/start))

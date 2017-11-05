(ns baoqu.core
  (:require [mount.core :as mount]
            [mount.core :refer [defstate]]
            [clojure.tools.namespace.repl :as repl]
            [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [catacumba.handlers.auth :as cauth]
            [baoqu.configuration :refer [config]]
            [baoqu.handlers.root :refer [sse-handler example-handler]]
            [baoqu.handlers.middleware :refer [cors-handler]]
            [baoqu.handlers.event :as eh]
            [baoqu.handlers.user :as uh]
            [baoqu.handlers.idea :as ih]
            [baoqu.handlers.circle :as ch]
            [baoqu.services.auth :refer [auth-backend]]))

(declare -main)

(defn- start
  []
  (-main))

(defn- stop
  []
  (mount/stop))

(def app
  (ct/routes [[:any #'cors-handler]
              [:any (cauth/auth auth-backend)]
              [:any (parse/body-params)]
              [:get "sse" #'sse-handler]
              [:prefix "api"
               [:any "" #'example-handler]
               [:post "login" #'uh/login]
               [:prefix "users"
                [:get ":id" #'uh/show]
                [:get ":id/path" #'uh/path]]
               [:get "user-circle/:id" #'ch/user-circle]
               [:prefix "events"
                [:get "" #'eh/list]
                [:get ":id" #'eh/show]
                [:get ":id/circles" #'eh/circles]
                [:get ":id/users" #'eh/users]
                [:post ":id/users" #'eh/add-user]
                [:get ":id/ideas" #'eh/ideas]
                [:get ":id/comments" #'eh/comments]
                [:get ":id/votes" #'eh/votes]]
               [:prefix "circles"
                [:get ":id/comments" #'ch/comments]
                [:post ":id/comments" #'ch/add-comment]
                [:get ":id/ideas" #'ch/ideas]]
               [:prefix "ideas"
                [:post "downvote" #'ih/downvote]
                [:post "upvote" #'ih/upvote]]]]))

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

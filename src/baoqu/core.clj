(ns baoqu.core
  (:require [mount.core :as mount]
            [mount.core :refer [defstate]]
            [clojure.tools.namespace.repl :as repl]
            [catacumba.core :as ct]
            [catacumba.handlers.parse :as parse]
            [baoqu.configuration :refer [config]]
            [baoqu.handlers.root :refer [sse-handler example-handler]]
            [baoqu.handlers.middleware :refer [cors-handler parse-token]]
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

(defstate routes
  :start (ct/routes [[:any #'cors-handler]
                     [:any #'parse-token]
                     [:any (parse/body-params)]
                     [:get "sse/:event-id" #'sse-handler]
                     [:prefix "api"
                      [:any "" #'example-handler]
                      [:post "login" #'uh/login]
                      [:post "register" #'uh/register]
                      [:prefix "users"
                       [:get ":id" #'uh/show]
                       [:get ":id/path" #'uh/path]]
                      [:prefix "events"
                       [:get "" #'eh/list]
                       [:get ":id" #'eh/show]
                       [:get ":id/circles" #'eh/circles]
                       [:get ":id/user-circle" #'ch/user-circle]
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
                       [:post "upvote" #'ih/upvote]]]])
  :stop nil)

(defn server-start
  []
  (let [server-port (:server-port config)]
    (ct/run-server routes {:port server-port})))

(defstate server
  :start (server-start)
  :stop (.stop server))

(defn -main
  [& args]
  (mount/start))

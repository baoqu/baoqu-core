(ns baoqu.configuration
  (:require [environ.core :refer [env]]))

(def default-database-path
  (str (System/getProperty "user.dir") "/baoqu-core-db.sqlite"))

(def config
  {:db-path (env :bc-db-path default-database-path)
   :server-port (Integer/parseInt (env :bc-server-port "5050"))
   :server-url (env :bc-server-url (str "http://localhost:" (env :bc-server-port "5050")))})

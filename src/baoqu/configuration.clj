(ns baoqu.configuration
  (:require [environ.core :refer [env]]))

(def default-database-path
  (str (System/getProperty "user.dir") "/baoqu-core-db.sqlite"))

(def default-basedir
  (str (System/getProperty "user.dir") "/resources/public"))

(def config
  {:db-path (env :bc-db-path default-database-path)
   :server-port (Integer/parseInt (env :bc-server-port "5050"))
   :server-url (env :bc-server-url (str "http://localhost:" (env :bc-server-port "5050")))
   :jwt-secret (env :bc-jwt-secret "0ea5a1f8-b722-49ac-b7d0-9cd1be1d85a5")
   :basedir (env :bc-basedir default-basedir)})

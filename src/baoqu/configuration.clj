(ns baoqu.configuration)

(defn env-or
  [env default]
  (or (System/getenv env) default))

(def default-database-path
  (str (System/getProperty "user.dir") "/baoqu-core-db.sqlite"))

(def config
  {:db-path (env-or "BC_DB_PATH" default-database-path)
   :server-port (Integer/parseInt (env-or "BC_SERVER_PORT" "5050"))
   :server-url (env-or "BC_SERVER_URL" "http://localhost:5050")})

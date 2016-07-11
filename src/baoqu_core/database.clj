(ns baoqu-core.database)

(def db-path "baoqu.db")

(def db {:classname "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname db-path})

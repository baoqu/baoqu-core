(ns baoqu.database
  (:require [baoqu.configuration :refer [config]]))

(def db {:classname "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname (:db-path config)})

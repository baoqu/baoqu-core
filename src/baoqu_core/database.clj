(ns baoqu-core.database
  (:require [baoqu-core.configuration :refer [config]]))

(def db {:classname "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname (:db-path config)})

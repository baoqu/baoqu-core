(ns baoqu-core.services.user
  (:require [baoqu-core.repos.user :as user-repo]))

(defn create [name]
  (user-repo/create name))

(defn get-by-id [id]
  (user-repo/get-by-id id))

(ns baoqu-core.services.circle
  (:require [baoqu-core.repos.circle :as circle-repo]))

(defn create [level parent-circle]
  {:id 1
   :level level
   :parent-circle parent-circle
   :user-ids []
   :main-idea nil})

(defn add-user [user circle])

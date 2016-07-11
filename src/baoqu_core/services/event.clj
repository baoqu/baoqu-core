(ns baoqu-core.services.event
  (:require [baoqu-core.repos.event :as event-repo]))

(defn create [name circle-size approval-factor]
  {:id 1
   :name name
   :circle-size circle-size
   :approval-factor approval-factor
   :revolver-user-ids []})

(defn all [filters]) ;; ?

(defn add-user [user event]
  )



;; SN 16207501094267

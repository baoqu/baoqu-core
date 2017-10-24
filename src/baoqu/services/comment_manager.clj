(ns baoqu.services.comment-manager
  (:require [baoqu.services.comment :as cs]
            [baoqu.async :refer [send-sse]]))

(defn create
  [user circle body]
  (let [new-comment (cs/create user circle body)
        circle-id (:id circle)
        payload (merge new-comment {:circle-id circle-id})]
    (send-sse payload "comment")
    new-comment))

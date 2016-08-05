(ns baoqu.services.comment-manager
  (:require [baoqu.services.comment :as comment-service]
            [baoqu.async :refer [send-sse]]))

(defn create
  [user circle body]
  (let [new-comment (comment-service/create user circle body)]
    (send-sse new-comment "comment")
    new-comment))

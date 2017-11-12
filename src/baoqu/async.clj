(ns baoqu.async
  (:require [clojure.core.async
             :refer [chan sliding-buffer mult >!!]]))

(def main-chan (chan (sliding-buffer 100)))
(def main-mult (mult main-chan))

(defn send-sse
  [data event-id type]
  (println ">>> Sending event with:")
  (println "    - Data:" data)
  (println "    - Event:" event-id)
  (println "    - Type:" type)
  (let [message {:data data :event-id event-id :type type}]
    (>!! main-chan message)))


; TODO IN FRONT
; - change :name for :username
; - connect to sse with event id
; - registration workflow
; - send jwt with every request if exists
; - is-registered? router checks for jwt token
; - change upvote and downvote requests to include event-id
; - change circle-service functions to use event-id
; - NOT PRIORITY start checking for user from token

(ns baoqu.async
  (:require [cheshire.core :refer [generate-string]]
            [clojure.core.async
             :refer [chan sliding-buffer mult >!!]]))

(def main-chan (chan (sliding-buffer 100)))
(def main-mult (mult main-chan))

(defn send-sse
  [data type]
  (println ">>> Sending event with:")
  (println "    - Data:" data)
  (println "    - Type:" type)

  ;; We need to add something to send each message only to its
  ;; relevant event (and even user). At the moment, sending messages
  ;; to events would be enough, so an "event-id" field may suffice

  (let [message (generate-string {:data data :type type})]
    (println "++++ PREVIOUS TO SEND TO main-chan")
    (>!! main-chan message)
    (println "++++ AFTER SENDING TO main-chan")))

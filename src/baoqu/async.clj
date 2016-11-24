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
  (let [message (generate-string {:data data :type type})]
    (>!! main-chan message)))

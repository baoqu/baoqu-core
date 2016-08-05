(ns baoqu.async
  (:require [cheshire.core :refer [generate-string]]
            [clojure.core.async
             :refer [chan sliding-buffer mult >!!]]))

(def main-chan (chan (sliding-buffer 10)))
(def main-mult (mult main-chan))

(defn send-sse
  [data type]
  (>!! main-chan (generate-string {:data data :type type})))

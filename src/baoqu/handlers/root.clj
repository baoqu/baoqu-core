(ns baoqu.handlers.root
  (:require [cheshire.core :refer [generate-string]]
            [clojure.core.async
             :refer [go-loop chan tap close! <! >! >!!]]
            [baoqu.async :refer [main-chan main-mult]]))

(defn example-handler
  [ctx]
  (let [query-params (:query-params ctx)
        msg (:msg query-params "NO MESSAGE")
        type (:type query-params "message")]
    (>!! main-chan (generate-string {:data msg :type type}))
    {:status 200
     :body (str "Message: \"" msg "\"\nType: \"" type "\"")}))

(defn sse-handler
  {:handler-type :catacumba/sse}
  [ctx out]
  (let [local-c (chan)
        _ (tap main-mult local-c)]
    (go-loop []
      (when-let [msg (<! local-c)]
        (if-not (>! out msg)
          (do
            (println "=================")
            (println "CLOSING A CHANNEL")
            (println "=================")
            (close! local-c))
          (recur))))))

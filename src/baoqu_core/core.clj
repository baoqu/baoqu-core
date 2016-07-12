(ns baoqu-core.core
  (:require [catacumba.core :as ct]
            [clojure.core.async :refer [go-loop chan mult tap close! <! >! >!!]]))

(def c (chan 10))
(def m (mult c))

(defn example-handler
  [ctx]
  (>!! c "WHATEVEEEEEEEEER")
  {:status 200
   :body "Hello BAOQU"})

(defn sse-handler
  {:handler-type :catacumba/sse}
  [ctx out]
  (let [local-c (chan)
        _ (tap m local-c)]
    (go-loop []
      (when-let [msg (<! local-c)]
        (if-not (>! out msg)
          (close! local-c)
          (recur))))))

(def app
  (ct/routes [[:any "sse" #'sse-handler]
              [:any "" example-handler]]))

(defn -main
  [& args]
  (ct/run-server app {:port 3030}))

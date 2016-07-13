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

(defn cors-handler
  [ctx]
  (ct/set-headers! ctx {:Access-Control-Allow-Origin "http://localhost:3449"})
  (ct/set-headers! ctx {:Access-Control-Expose-Headers "*"})
  (ct/set-headers! ctx {:Access-Control-Allow-Credentials "true"})
  (ct/delegate))

(def app
  (ct/routes [[:prefix "api"
               [:any cors-handler]
               [:get "sse" #'sse-handler]
               [:any "" example-handler]]]))

(defn -main
  [& args]
  (ct/run-server app {:port 3030}))

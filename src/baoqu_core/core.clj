(ns baoqu-core.core
  (:require [catacumba.core :as ct]
            [cheshire.core :refer [generate-string]]
            [catacumba.handlers.parse :as parse]
            [clojure.core.async
             :refer [go-loop chan sliding-buffer mult tap close! <! >! >!!]]))

(def c (chan (sliding-buffer 10)))
(def m (mult c))

(defn example-handler
  [ctx]
  (let [query-params (:query-params ctx)
        msg (:msg query-params "NO MESSAGE")
        type (:type query-params "message")]
    (>!! c (generate-string {:data msg :type type}))
    {:status 200
     :body (str "Message: \"" msg "\"\nType: \"" type "\"")}))

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
  (ct/routes [[:any #'cors-handler]
              [:any (parse/body-params)]
              [:get "sse" #'sse-handler]
              [:prefix "api"
               [:any "" #'example-handler]]]))

(defn -main
  [& args]
  (ct/run-server app {:port 3030}))

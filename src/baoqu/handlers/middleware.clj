(ns baoqu.handlers.middleware
  (:require [catacumba.core :as ct]))

(defn cors-handler
  [ctx]
  (ct/set-headers! ctx {:Access-Control-Allow-Origin "*"})
  (ct/set-headers! ctx {:Access-Control-Expose-Headers "*"})
  (ct/set-headers! ctx {:Access-Control-Allow-Headers "X-Requested-With, Content-Type"})
  (ct/set-headers! ctx {:Access-Control-Allow-Methods "GET, POST, PUT, DELETE, OPTIONS"})
  (if (= (:method ctx) :options)
    {:status 200
     :body ""}
    (ct/delegate)))

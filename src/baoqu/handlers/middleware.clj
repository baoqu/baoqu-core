(ns baoqu.handlers.middleware
  (:require [catacumba.core :as ct]
            [cuerdas.core :as str]
            [baoqu.services.auth :as as]))

(defn cors-handler
  [ctx]
  (ct/set-headers! ctx {:Access-Control-Allow-Origin "*"})
  (ct/set-headers! ctx {:Access-Control-Expose-Headers "*"})
  (ct/set-headers! ctx {:Access-Control-Allow-Headers "X-Requested-With, Content-Type, Authorization"})
  (ct/set-headers! ctx {:Access-Control-Allow-Methods "GET, POST, PUT, DELETE, OPTIONS"})
  (if (= (:method ctx) :options)
    {:status 200
     :body ""}
    (ct/delegate)))

(defn- clean-token
  [token]
  (if (str/starts-with? token "Bearer ")
    (.replace token "Bearer " "")
    token))

(defn parse-token
  [ctx]
  (let [authorization (get-in ctx [:headers :authorization])
        token (clean-token authorization)
        identity (as/unsign token)]
    (ct/delegate {:identity identity})))

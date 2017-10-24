(ns baoqu.handlers.user
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.user :as us]))

(defn path
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (us/get-by-id id)]
    (json 200 (us/get-user-path user))))

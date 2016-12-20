(ns baoqu.handlers.user
  (:require [baoqu.http-utils :refer [json]]
            [baoqu.services.user :as user-service]))

(defn path
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (user-service/get-by-id id)]
    (json 200 (user-service/get-user-path user))))

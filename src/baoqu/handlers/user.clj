(ns baoqu.handlers.user
  (:require [catacumba.core :as ct]
            [baoqu.http-utils :refer [json]]
            [baoqu.services.user :as us]
            [baoqu.services.auth :as as]))

(defn login
  [ctx]
  (let [name (get-in ctx [:data :name])
        password (get-in ctx [:data :password])
        {:keys [id]} (us/get-by-username name)]
    (if (us/authenticate name password)
      ;; generate and return token -- TEMP -- return ok or ko
      (json 200 {:status "ok" :id id :name name})
      (json 401 {:status "ko"}))))

(defn cookie-login
  [ctx]
  (ct/set-cookies! ctx {:auth {:value "whoever"}})
  (json 200 {:token (as/sign {:username "Mike" :password "Wachownsky"})}))

(defn cookie-check-login
  [ctx]
  (println "==========================")
  (println ctx)
  (let [authorization (get-in ctx [:headers :authorization])]
    (println (as/unsign authorization)))
  (println "==========================")
  (json 200 (get-in ctx [:cookies :auth :value])))

(defn path
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (us/get-by-id id)]
    (json 200 (us/get-user-path user))))

(defn show
  [ctx]
  (let [id (get-in ctx [:route-params :id])
        user (us/get-by-id id)]
    (json 200 user)))

(ns baoqu.handlers.user
  (:require [catacumba.core :as ct]
            [baoqu.http-utils :refer [json]]
            [baoqu.services.user :as us]
            [baoqu.services.auth :as as]))

(defn login
  [ctx]
  (let [username (get-in ctx [:data :username])
        password (get-in ctx [:data :password])
        {:keys [id]} (us/get-by-username username)
        authenticated? (us/authenticate username password)
        data {:id id :username username}]
    (if authenticated?
      (json 200 {:status "ok" :id id :username username :token (as/sign data)})
      (json 401 {:status "ko"}))))

(defn register
  [ctx]
  (let [username (get-in ctx [:data :username])
        password (get-in ctx [:data :password])]
    (if-not (us/get-by-username username)
      (do
        (us/create username password)
        (json 201))
      (json 400 {:message "User already exists"}))))

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

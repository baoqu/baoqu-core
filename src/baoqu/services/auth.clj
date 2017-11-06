(ns baoqu.services.auth
  (:require [catacumba.handlers.auth :as cauth]
            [buddy.sign.jwt :as jwt]
            [buddy.hashers :as hashers]
            [baoqu.configuration :refer [config]]))

(def auth-backend
  (cauth/jws-backend {:secret (:jwt-secret config)}))

(defn sign
  [data]
  (jwt/sign data (:jwt-secret config)))

(defn unsign
  [token]
  (jwt/unsign token (:jwt-secret config)))

(defn encrypt
  [data]
  (hashers/derive data))

(defn check
  [expected-data data]
  (hashers/check expected-data data))

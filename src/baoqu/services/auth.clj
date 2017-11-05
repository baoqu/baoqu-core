(ns baoqu.services.auth
  (:require [catacumba.handlers.auth :as cauth]
            [buddy.sign.jwt :as jwt]
            [buddy.hashers :as hashers]))

(def secret "mysupersecret")

(def auth-backend
  (cauth/jws-backend {:secret secret}))

(defn sign
  [data]
  (jwt/sign data secret))

(defn unsign
  [token]
  (jwt/unsign token secret))

(defn encrypt
  [data]
  (hashers/derive data))

(defn check
  [expected-data data]
  (hashers/check expected-data data))

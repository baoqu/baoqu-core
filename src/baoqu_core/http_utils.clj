(ns baoqu-core.http-utils
  (:require [cheshire.core :refer [generate-string]]))

(def default-headers {"Content-Type" "application/json"})

(defn json
  ([status body opts]
   (let [str-body (generate-string body)
         headers (merge (:headers opts) default-headers)]
     {:status status
      :body str-body
      :headers headers}))
  ([status body]
   (json status body {}))
  ([status]
   {:status status
    :body ""
    :headers default-headers}))

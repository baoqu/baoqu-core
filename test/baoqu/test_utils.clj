(ns baoqu.test-utils
  (:require [cheshire.core :refer [parse-string]]
            [clojure.walk :refer [keywordize-keys]]))

(defn jsonstr->clj
  [jsonstr]
  (-> jsonstr
      parse-string
      keywordize-keys))

(defn parse-res
  [{:keys [body] :as res}]
  (assoc res :body (jsonstr->clj body)))

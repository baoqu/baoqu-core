(ns baoqu-core.core
  (:require [catacumba.core :as ct]
            [catacumba.handlers.misc :as misc]))

(defn example-handler
  [ctx]
  {:status 200
   :body "Hello BAOQU"})

(def app
  (ct/routes [[:any (misc/autoreloader)]
              [:all "" #'example-handler]]))

(defn -main
  [& args]
  (ct/run-server app {:port 3030}))

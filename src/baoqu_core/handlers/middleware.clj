(ns baoqu-core.handlers.middleware
  (:require [catacumba.core :as ct]))

(defn cors-handler
  [ctx]
  (ct/set-headers! ctx {:Access-Control-Allow-Origin "http://localhost:3449"})
  (ct/set-headers! ctx {:Access-Control-Expose-Headers "*"})
  (ct/set-headers! ctx {:Access-Control-Allow-Credentials "true"})
  (ct/delegate))

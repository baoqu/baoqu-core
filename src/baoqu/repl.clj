(ns baoqu.repl
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [mount.core :as mount]
            [baoqu.core :as core]))

(defn start
  []
  (mount/start))

(defn stop
  []
  (mount/stop))

(defn restart
  []
  (stop)
  (refresh :after 'baoqu.repl/start))

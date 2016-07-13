(ns baoqu-core.async
  (:require [clojure.core.async
             :refer [chan sliding-buffer mult]]))

(def main-chan (chan (sliding-buffer 10)))
(def main-mult (mult main-chan))

(ns baoqu.handlers.root
  (:require [cheshire.core :refer [generate-string]]
            [clojure.core.async
             :refer [go-loop chan tap close! <! >! >!! timeout alts!]]
            [baoqu.async :refer [main-chan main-mult]]))

(defn example-handler
  [ctx]
  (let [query-params (:query-params ctx)
        msg (:msg query-params "NO MESSAGE")
        type (:type query-params "message")]
    (>!! main-chan (generate-string {:data msg :type type}))
    {:status 200
     :body (str "Message: \"" msg "\"\nType: \"" type "\"")}))

(defn sse-handler
  {:handler-type :catacumba/sse}
  [ctx out]
  (let [local-c (chan)
        _ (tap main-mult local-c)]
    (go-loop []
      (when-let [msg (<! local-c)]
        (if-not (>! out msg)
          (do
            (println "=================")
            (println "CLOSING A CHANNEL")
            (println "=================")
            (close! local-c))
          (recur))))))

;; (defn sse-handler
;;   {:handler-type :catacumba/sse}
;;   [ctx out]
;;   (let [local-c (tap main-mult (chan))
;;         id (str (gensym "kaka"))]
;;     (go-loop []
;;       (println "--" id "-- GOLOOP")
;;       (let [msg (<! local-c)
;;             _ (println "--" id "-- Received msg:" msg)
;;             [result port] (alts! [(timeout 500) [out msg]])]
;;         (println "--" id "-- SENT" (not= port out))
;;       	(if (= port out)
;;       	  (if result (recur))
;;           (do
;;             (println "=================")
;;             (println "CLOSING A CHANNEL")
;;             (println "=================")
;;             (close! out)
;;             (close! local-c)))))))

;; (defn sse-handler
;;   {:handler-type :catacumba/sse}
;;   [ctx out]
;;   (let [local-c (tap main-mult (chan))]
;;     (go-loop []
;;       (let [[msg ch] (alts! [local-c (timeout 2000)])]
;;         (if (= ch local-c)
;;           (let [[result port] (alts! [(timeout 200) [out msg]])]
;;             (if (= port out)
;;               (when result (recur))
;;               (do
;;                 (println "===================")
;;                 (println "CLOSING A CHANNEL 1")
;;                 (println "===================")
;;                 (close! out)
;;                 (close! local-c))))
;;           (do
;;             (println "===================")
;;             (println "CLOSING A CHANNEL 2")
;;             (println "===================")
;;             (close! out)
;;             (close! local-c)))))))

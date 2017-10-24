(ns baoqu.handlers.events-test
  (:require [clojure.test :refer :all]
            [baoqu.test-utils :refer [parse-res]]
            [baoqu.handlers.event :as he]))

(deftest event-list
  (testing "That without events, the handler returns an empty list"
    (with-redefs [baoqu.repos.event/get-all #(identity [])]
      (let [{:keys [status body] :as res} (parse-res (he/list {}))]
        (is (= status 200))
        (is (= body [])))))

  (testing "That with events, the handler returns a list of the events"
    (let [fake-events {:name "event" :whatever 28}]
      (with-redefs [baoqu.repos.event/get-all #(identity fake-events)]
        (let [{:keys [status body] :as res} (parse-res (he/list {}))]
          (is (= status 200))
          (is (= body fake-events)))))))

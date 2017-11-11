(ns baoqu.handlers.events-test
  (:require [clojure.test :refer :all]
            [baoqu.test-utils :refer [parse-res]]
            [baoqu.handlers.event :as eh]))

(deftest event-list
  (testing "That without events, the handler returns an empty list"
    (with-redefs [baoqu.repos.event/get-all #(identity [])]
      (let [{:keys [status body] :as res} (parse-res (eh/list {}))]
        (is (= status 200))
        (is (= body [])))))

  (testing "That with events, the handler returns a list of the events"
    (let [fake-events {:name "event" :whatever 28}]
      (with-redefs [baoqu.repos.event/get-all #(identity fake-events)]
        (let [{:keys [status body] :as res} (parse-res (eh/list {}))]
          (is (= status 200))
          (is (= body fake-events)))))))

(deftest add-user
  (let [add-user-called? (atom false)
        invert-atom-fn (fn [_ _] (swap! add-user-called? not))]
    (testing "If event doesn't exist, user is not added"
      (with-redefs [baoqu.services.event-manager/add-user-to-event invert-atom-fn
                    baoqu.services.user/get-user-from-ctx (fn [_] nil)
                    baoqu.services.event/get-by-id (fn [_] {:id 1})]
        (let [{:keys [status]} (eh/add-user {})]
          (is (= 404 status))
          (is (not @add-user-called?)))))
    (testing "If user doesn't exist, is not added"
      (with-redefs [baoqu.services.event-manager/add-user-to-event invert-atom-fn
                    baoqu.services.user/get-user-from-ctx (fn [_] {:id 1})
                    baoqu.services.event/get-by-id (fn [_] nil)]
        (let [{:keys [status]} (eh/add-user {})]
          (is (= 404 status))
          (is (not @add-user-called?)))))
    (testing "If the parameters are good, the user is added"
      (with-redefs [baoqu.services.event-manager/add-user-to-event invert-atom-fn
                    baoqu.services.user/get-user-from-ctx (fn [_] {:id 1})
                    baoqu.services.event/get-by-id (fn [_] {:id 1})]
        (let [{:keys [status]} (eh/add-user {})]
          (is (= 200 status))
          (is @add-user-called?))))))

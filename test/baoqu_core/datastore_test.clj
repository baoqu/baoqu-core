(ns baoqu-core.datastore-test
  (require [clojure.test :refer :all]
           [baoqu-core.datastore :refer :all]))

;; Helper functions
;; TODO: Move helpers to fixtures
(defn h-create-event []
  (create-event 1 "whatever" 3 3))

(defn h-create-user []
  (create-user 1 "name"))


;; Tests
(deftest test-create-new-event
  (let [res (h-create-event)]
    (testing "The new event is a Map"
      (is (instance? clojure.lang.PersistentArrayMap res)))
    (testing "The new event is not empty"
      (is (not (= {} res))))))

(deftest test-add-first-user-to-event
  (let [event (h-create-event)
        user (h-create-user)
        user-id (:id user)
        res (add-user-to-event event user)]
    (testing "The added user is the only one inside the event"
      (let [res-user-ids (:user-ids res)
            expected-user-ids (set (list user-id))]
        (is (= res-user-ids expected-user-ids))))))

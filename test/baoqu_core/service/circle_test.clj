(ns baoqu-core.service.circle-test
  (require [clojure.test :refer :all]
           [baoqu-core.service.circle :as s]))

(deftest create-level-one-test
  "Creating a circle without parents"
  []
  (let [circle-keys [:id :level :parent-circle :user-ids :main-idea]
        level 1
        parent-circle nil
        res (s/create level parent-circle)]

    (testing "Has all corresponding keys"
      (doseq [k circle-keys]
        (is (contains? res k))))

    (testing "The resulting key is the expected"
      (is (= res {:id 1
                  :level level
                  :parent-circle parent-circle
                  :user-ids []
                  :main-idea nil})))

    (testing "An id has been assigned"
      (is (number? (:id res))))))

(deftest add-user-to-empty-circle-test []
  (let [circle (s/create 1 nil)
        user {:id 1 :name "Miguel"}]

    (testing "The circle starts empty"
      (is (empty? (:user-ids circle))))

    (testing "The user is added correctly"
      (let [res (s/add-user user circle)]
        (is (not (empty? (:user-ids circle))))
        (is (contains? (:user-ids circle) 1))))))

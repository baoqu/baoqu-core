(ns baoqu-core.service.event-test
  (require [clojure.test :refer :all]
           [baoqu-core.service.event :as s]))

(deftest create-test []
  (let [name "name"
        circle-size 3
        approval-factor 2
        res (s/create name circle-size approval-factor)]

    (testing "All keys are present"
      (let [event-keys [:id :name :circle-size :approval-factor :revolver-user-ids]]
        (doseq [k event-keys]
          (is (contains? res k)))))

    (testing "An id has been assigned"
      (is (number? (:id res))))))

(deftest add-user-to-empty-event-test []
  (let [event (s/create "name" 3 2)
        user {:name "Miguel"}
        res (s/add-user user event)]

    (testing "The event now has a circle and one user")

  ))

(deftest add-user-to-event-with-full-circles-test []
  )

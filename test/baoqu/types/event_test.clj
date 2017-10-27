(ns baoqu.types.event-test
  (:require [clojure.test :refer :all]
            [baoqu.types.event :as et]))

(deftest event-creation
  (testing "That if we try to create an event with the correct parameters, we can"
    (let [id 1
          name "My event"
          description "This is my event"
          circle-size 3
          agreement-factor 3
          event (et/event id name description circle-size agreement-factor)]
      (is (et/is-event? event))
      (is (= (:id event) id))
      (is (= (:name event) name))
      (is (= (:circle-size event) circle-size))
      (is (= (:agreement-factor event) agreement-factor))))

  (testing "That if we use bad parameters, the creation fails"
    (let [id 1
          name "My event"
          description "This is my event"
          circle-size 3
          agreement-factor 3]
      (is (thrown? java.lang.AssertionError (et/event "one" name description circle-size agreement-factor)))
      (is (thrown? java.lang.AssertionError (et/event id {} description circle-size agreement-factor)))
      (is (thrown? java.lang.AssertionError (et/event id name 3 circle-size agreement-factor)))
      (is (thrown? java.lang.AssertionError (et/event id name description "three" agreement-factor)))
      (is (thrown? java.lang.AssertionError (et/event id name description circle-size [])))))

  (testing "That if we try to create an event from a map, we can"
    (let [id 1
          name "My event"
          description "This is my event"
          circle-size 3
          agreement-factor 3
          data {:id 1
                :name "My event"
                :description "This is my event"
                :circle-size 3
                :agreement-factor 3}
          event (et/event data)]
      (is (et/is-event? event))
      (is (= (:id event) id))
      (is (= (:name event) name))
      (is (= (:circle-size event) circle-size))
      (is (= (:agreement-factor event) agreement-factor)))))

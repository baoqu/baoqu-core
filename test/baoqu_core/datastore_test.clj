(ns baoqu-core.datastore-test
  (:require [clojure.test :refer :all]
            [baoqu-core.datastore :refer :all]))

(deftest test-create-new-event
  (let [res (create-event 1)]
    (testing "The new event is an atom"
      (is (instance? clojure.lang.Atom res)))
    (testing "The new event is not empty"
      (is (not (= {} @res))))))

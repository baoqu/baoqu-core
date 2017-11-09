(ns baoqu.services.auth-test
  (:require [clojure.test :refer :all]
            [baoqu.services.auth :refer :all]))

(deftest sign-and-unsign
  (testing "That sign and unsign work correctly"
    (let [data {:username "johndoe"}
          token (sign data)
          unsigned-data (unsign token)]
      (is (= data unsigned-data))))
  (testing "That if the token is wrong, the unsign will return nil"
    (let [bad-token "imma bad token"
          unsigned-data (unsign bad-token)]
      (is (= nil unsigned-data)))))

(deftest encrypt-and-check
  (testing "That I can encrypt a string and test for it"
    (let [data "mysupersecret"
          encrypted-data (encrypt data)]
      (is (true? (check data encrypted-data))))))

(deftest dummy-check
  (testing "That I can mock something"
    (with-redefs [buddy.hashers/derive (fn [x] "MY DATA")]
      (is (= "MY DATA" (encrypt "Whatever"))))))

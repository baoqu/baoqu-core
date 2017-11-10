(ns baoqu.handlers.user-test
  (:require [clojure.test :refer :all]
            [baoqu.handlers.user :as uh]))

(deftest register-test
  (testing "I can create a user"
    (with-redefs [baoqu.repos.user/get-by-username (fn [_] nil)
                  baoqu.repos.user/create (fn [a b] {})]
      (let [username "johndoe"
            password "secret"
            ctx {:data {:username username :password password}}
            ctx (uh/register ctx)]
        (is (= 201 (:status ctx))))))
  (testing "I cannot create an already existing user"
    (with-redefs [baoqu.repos.user/get-by-username (fn [_] {:username "johndoe"})]
      (let [username "johndoe"
            password "secret"
            ctx {:data {:username username :password password}}
            ctx (uh/register ctx)]
        (is (= 400 (:status ctx)))))))

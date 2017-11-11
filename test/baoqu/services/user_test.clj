(ns baoqu.services.user-test
  (:require [clojure.test :refer :all]
            [baoqu.services.user :as us]))

(deftest get-user-from-ctx-test
  (testing "I can retrieve a user from the request ctx"
    (let [id 1
          username "johndoe"
          fake-user-data {:id id :username username}
          fake-ctx {:identity {:id 1 :username "johndoe"}}]
      (with-redefs [baoqu.services.user/get-by-id (fn [user-id]
                                                    (is (= id user-id))
                                                    fake-user-data)]
        (= fake-user-data (us/get-user-from-ctx fake-ctx))))))

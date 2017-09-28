(ns baoqu.services.event-manager-tests
  (:require [clojure.test :as t]
            [baoqu.services.event-manager :as ems]))

(t/deftest show-event-detail-test
  []
  (t/testing
      "When receiving a good event, the resulting map should be correctly built"
    (let [mock-circles "mock-circles"
          mock-event "mock-event"
          expected {:event mock-event
                    :circles mock-circles}]
      (with-redefs-fn {#'circle-service/get-all-for-event
                       #(identity mock-circles)}
        (t/is (ems/show-event-detail mock-event) expected)))))

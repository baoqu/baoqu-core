(ns baoqu-core.utils)

(defn get-leveled-agreement-factor
  [circle-size level agreement-factor]
  (let [prev-level (- level 1)]
    (* (int (Math/pow circle-size prev-level)) agreement-factor)))

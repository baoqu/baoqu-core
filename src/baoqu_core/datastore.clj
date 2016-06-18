(ns baoqu-core.datastore)

(defn create-event
  "Creates a new event"
  [id name circle-size approval-factor]
  (atom {:id id}))


;; {:event {:id 1
;;          :name "Whatever"
;;          :circle-size 3
;;          :approval-factor 2}
;;    :my-circle 1
;;    :active-section "ideas"
;;    :circles {1 {:id 1
;;                 :name "My circle"
;;                 :participants [1 2]
;;                 :parent-circle nil
;;                 ;; hay que calcular:
;;                 ;; - most-popular-idea
;;                 ;; - most-popular-idea-votes
;;                 ;; - num-comments
;;                 ;; - num-ideas
;;                 }
;;              2 {:id 2
;;                 :name "A second circle"
;;                 :participants [3 4]
;;                 :parent-circle nil}}
;;    :ideas {1 {:id 1
;;               :body "My idea"
;;               :votes 1
;;               :circle-id 1
;;               ;; Nos hemos cargado is-voted en favor de
;;               ;; tener una colección de votos
;;               }
;;            2 {:id 2
;;               :body "My second idea"
;;               :votes 0
;;               :circle-id 1}}
;;    :comments {1 {:id 1
;;                  :author 1
;;                  :idea-id 1
;;                  :body "Testing comments"
;;                  :date "2015-12-15T16:24:38.395Z"}}
;;    :votes {1 {:id 1
;;               :user-id 1
;;               :idea-id 1}}
;;    :participants {1 {:id 1
;;                      :name "Miguel"}
;;                   2 {:id 2
;;                      :name "Mario"}
;;                   3 {:id 3
;;                      :name "Andy"}
;;                   4 {:id 4
;;                      :name "Miguel2"}}
;;    :session {:username "Miguel"}}

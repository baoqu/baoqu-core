(defproject baoqu-core "0.1-SNAPSHOT"
  :description "The core logic of Baoqu"
  :url "http://baoqu.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main baoqu-core.core
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :plugins [[lein-cucumber "1.0.2"]
            [lein-codox "0.9.5"]]
  :codox {:source-paths ["src"]
          :language :clojure
          :metadata {:doc/format :markdown}})

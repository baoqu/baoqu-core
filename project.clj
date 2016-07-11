(defproject baoqu-core "0.1-SNAPSHOT"
  :description "The core logic of Baoqu"
  :url "http://baoqu.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aot :all
  :main baoqu-core.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.layerware/hugsql "0.4.7"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]]
  :plugins [[lein-cucumber "1.0.2"]
            [lein-codox "0.9.5"]]
  :aliases {"create-db" ["run" "-m" "baoqu-core.database.scripts/safely-create"]
            "remove-db" ["run" "-m" "baoqu-core.database.scripts/delete"]
            "reload-db" ["run" "-m" "baoqu-core.database.scripts/reload"]}
  :codox {:source-paths ["src"]
          :language :clojure
          :metadata {:doc/format :markdown}})

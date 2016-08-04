(defproject baoqu-core "0.1-SNAPSHOT"
  :description "The core logic of Baoqu"
  :url "http://baoqu.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.layerware/hugsql "0.4.7"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [funcool/catacumba "0.17.0"]
                 [org.slf4j/slf4j-simple "1.7.21"]]
  :plugins [[lein-cucumber "1.0.2"]
            [lein-codox "0.9.5"]]
  :aliases {"create-db" ["run" "-m" "baoqu-core.database.scripts/safely-create"]
            "remove-db" ["run" "-m" "baoqu-core.database.scripts/delete"]
            "reload-db" ["run" "-m" "baoqu-core.database.scripts/reload"]
            "srv"       ["run" "-m" "baoqu-core.core/-main"]}
  :codox {:source-paths ["src"]
          :language :clojure
          :metadata {:doc/format :markdown}})

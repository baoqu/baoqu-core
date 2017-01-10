(defproject baoqu-core "0.1-SNAPSHOT"
  :description "The core logic of Baoqu"
  :url "http://baoqu.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [com.layerware/hugsql "0.4.7"]
                 [org.xerial/sqlite-jdbc "3.15.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [funcool/catacumba "1.2.0"]
                 [org.slf4j/slf4j-simple "1.7.22"]
                 [environ "1.1.0"]]
  :plugins [[lein-cucumber "1.0.2"]
            [lein-codox "0.9.5"]
            [lein-ancient "0.6.10"]]
  :aliases {"create-db" ["run" "-m" "baoqu.database.scripts/safely-create"]
            "remove-db" ["run" "-m" "baoqu.database.scripts/delete"]
            "reload-db" ["run" "-m" "baoqu.database.scripts/reload"]
            "srv"       ["run" "-m" "baoqu.core/-main"]}
  :codox {:source-paths ["src"]
          :language :clojure
          :metadata {:doc/format :markdown}})

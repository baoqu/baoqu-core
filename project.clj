(defproject baoqu-core "0.1-SNAPSHOT"
  :description "The core logic of Baoqu"
  :url "http://baoqu.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha15"]
                 [com.layerware/hugsql "0.4.7"]
                 [org.xerial/sqlite-jdbc "3.19.3"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [funcool/catacumba "2.2.0"]
                 [org.slf4j/slf4j-simple "1.7.25"]
                 [environ "1.1.0"]
                 [mount "0.1.11"]]
  :plugins [[lein-cucumber "1.0.2"]
            [lein-codox "0.9.5"]
            [lein-ancient "0.6.10"]
            [lein-marginalia "0.9.0"]]
  :aliases {"create-db" ["run" "-m" "baoqu.database.scripts/safely-create"]
            "remove-db" ["run" "-m" "baoqu.database.scripts/delete"]
            "reload-db" ["run" "-m" "baoqu.database.scripts/reload"]
            "srv"       ["run" "-m" "baoqu.core/-main"]}
  :codox {:source-paths ["src"]
          :language :clojure
          :metadata {:doc/format :markdown}}
  :profiles {:repl {:main baoqu.repl}})

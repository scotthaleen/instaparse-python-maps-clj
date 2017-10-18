(require '[clojure.string :as s]
         '[clojure.java.shell :refer [sh]])

(def VERSION "0.1.0-SNAPSHOT")


(def short-hash
  (try
    (let [{:keys [out exit]}
          (sh "git" "rev-parse" "--short" "HEAD")]
      (if (= 0 exit) (s/trim out) ""))
    (catch Exception e
      (println "WARNING: error occured parsing git revision"))))


(defproject instaparse-python VERSION
  :description "instaparer for python maps"
  :url "https://github.com/scotthaleen/instaparse-python-maps-clj"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [instaparse "1.4.8"]]

  :jar-name ~(str
              "instaparse-python-%s"
              (if (not-empty short-hash) (str "-" short-hash))
              ".jar")

  :profiles {
             :lint [:project/lint]
             :project/lint {:plugins [[jonase/eastwood "0.2.5"]]}}
  :aliases {
            "lint" ["with-profile" "lint" "eastwood"]
            "travis-ci" ["do" ["clean"] ["lint"] ["test"] ["jar"]]}  )

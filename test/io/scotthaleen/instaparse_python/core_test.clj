(ns io.scotthaleen.instaparse-python.core-test
  (:require
   [clojure.test :refer :all]
   [instaparse.core :as insta]
   [io.scotthaleen.instaparse-python.core :refer [parser]]))


(deftest python-map-test

  (testing "empty object"
    (is
     (=
      (list [:object])
      (parser "{}"))))

  (testing "simple map parsing"
    (is
     (=
      (list [:object [:pair [:keyword "age"] [:value [:number "25"]]]])
      (parser "{u'age': 25}"))))

  (testing "negative numbers"
    (is
     (=
      (list [:object [:pair [:keyword "k"] [:value [:number "-42"]]]])
      (parser "{'k': -42 }" ))))

  (testing "decimal numbers"
    (is
     (=
      (list [:object [:pair [:keyword "k"] [:value [:decimal "1.012"]]]])
      (parser "{'k': 1.012 }" ))))

  (testing "negative decimal numbers"
    (is
     (=
      (list [:object [:pair [:keyword "k"] [:value [:decimal "-99.12"]]]])
      (parser "{'k': -99.12 }" ))))


  (testing "value with unicode char"
    (is
     (=
      (list [:object [:pair [:keyword "s"] [:value [:string "abc"]]]])
      (parser "{'s': u'abc'}"))))

  (testing "string with whitespace"
    (is
     (=
      (list [:object [:pair [:keyword "k"] [:value [:string "a b c"]]]])
      (parser "{'k': 'a b c'}"))))

  (testing "map with boolean values"
    (is
     (=
      (list [:object
             [:pair [:keyword "t"] [:value [:boolean "True"]]]
             [:pair [:keyword "f"] [:value [:boolean "False"]]]])
      (parser "{'t': True, 'f': False }"))))

  (testing "map with nulls (None)"
    (is
     (=
      (list [:object
             [:pair [:keyword "k"] [:value [:none "None"]]]])
      (parser "{'k': None }"))))

  (testing "complex nested map"
    (is
     (=
      (list
       [:object
        [:pair [:keyword "foo"]
         [:value [:array
                  [:value [:number "1"]]
                  [:value [:number "2"]]
                  [:value [:number "3"]]
                  [:value [:number "4"]]]]]
        [:pair
         [:keyword "bar"]
         [:value
          [:object
           [:pair [:keyword "baz"]
            [:value [:array
                     [:value [:string "a"]]
                     [:value [:string "ab"]]
                     [:value [:string "abc"]]]]]]]]])
      (parser "{u'foo': [1, 2, 3, 4], u'bar': {u'baz': ['a', 'ab', 'abc']}}")))))


(deftest python-map-failing-cases
  (testing "empty string"
    (is (insta/failure? (parser ""))))

  (testing "keyword with number fails"
    (is
     (insta/failure? (parser "{'abc1': 'xyz'}"))))

  (testing "escaped string characters throw exception"
    (is
     (insta/failure? (parser  "{'abc' : 'ab''c'}")))))





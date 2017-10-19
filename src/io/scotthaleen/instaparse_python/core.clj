(ns io.scotthaleen.instaparse-python.core
  (:require [instaparse.core :as insta]))

(def parser
  (insta/parser
         "
          <S> ::= object
          <members> ::= <whitespace>? pair  | <whitespace>? pair <','> <whitespace>? members
          object ::= <'{'> <'}'>  | <'{'> members <'}'>
          array ::= <'['> <']'> | <'['> elements <']'>
          pair ::= keyword <':'> <whitespace>? value <whitespace>?
          <elements> ::= value | value <','> <whitespace>? elements
          <quote> = <'\\''>
          <string_ascii> =  #'[^\\']+'
          string = <'u'>? quote string_ascii quote
          keyword = <'u'>? quote #'[a-zA-Z]+' quote
          number = #'-?[0-9]+'
          decimal = #'-?[0-9]+\\.[0-9]+'
          boolean = 'True' | 'False'
          none = 'None'
          value ::= string | number | decimal | object | array | boolean | none
          whitespace = #'\\s+'
"))

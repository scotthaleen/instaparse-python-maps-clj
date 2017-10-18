# instaparse python maps clj

A _good enough_ EBNF [instaparser](https://github.com/Engelberg/instaparse) to parse simple python maps to clojure data structures


[![Build Status](https://travis-ci.org/scotthaleen/instaparse-python-maps-clj.svg?branch=master)](https://travis-ci.org/scotthaleen/instaparse-python-maps-clj)


Often there are cases when python maps have been dumped directly to a file. This is a simple parser to parse that data in to clojure data structures.


### Usage

```python

>>> {u'age': 25}
>>> {u'bar': { u'baz': ['a', 'ab', 'abc']}, u'foo': [1, 2, 3, 4]}

```

```clojure

(require [io.scotthaleen.instaparse-python.core :refer [parser]])

(parser "{u'age': 25}")

;;=> ([:object [:pair [:keyword "age"] [:value [:number "25"]]]])

(parser "{u'bar': { u'baz': ['a', 'ab', 'abc']}, u'foo': [1, 2, 3, 4]}")

;;=> ([:object [:pair [:keyword "foo"] [:value [:array [:value [:number "1"]] [:value [:number "2"]] [:value [:number "3"]] [:value [:number "4"]]]]] [:pair [:keyword "bar"] [:value [:object [:pair [:keyword "baz"] [:value [:array [:value [:string "a"]] [:value [:string "ab"]] [:value [:string "abc"]]]]]]]]])
```

### TODO

- [ ] decimals
- [ ] escaped characters in strings
- [ ] reconstruct clojure maps from parser output

### License

Copyright © 2017 ☕

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

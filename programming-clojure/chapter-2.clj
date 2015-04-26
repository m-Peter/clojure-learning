;; Forms
42
[1 2 3]
(+ 1 2) ;; function call using prefix notation
(concat [1 2] [3 4])
(+ 1 2 3)

;; Clojure forms

;; Boolean
true
false

;; Character
\a
\b

;; Keyword
:tag
:doc

;; List
'(1 2 3)
(println "foo")

;; Map
{:name "Bill" :age 42}

;; Nil
nil

;; Number
1
4.2

;; Set
#{:snap :crackle :pop}

;; String
"hello"

;; Symbol
clojure.core/set
java.lang.String

;; Vector
[1 2 3]

(+)
(*)

(- 10 5)
(* 3 10 10)
(> 5 2)
(>= 5 5)
(< 5 2)
(= 5 2)
(/ 22 7)
(class (/ 22 7))
(/ 22.0 7)
(quot 22 7)
(rem 22 7)
(+ 1 (/ 0.00001 1000000000000000))
(+ 1 (/ 0.00001M 1000000000000000))
(* 1000N 1000 1000 1000 1000 1000 1000)

;; Strings and Characters
"This is\n a multiline string"
"This is also
a multiline string"

(println "Another\nmultiline\nstring")
(.toUpperCase "hello")

(str 1 2 nil 3)
(str \h \e \y \space \y \o \u)
(Character/toUpperCase \s)

(interleave "Attack at midnight" "The purple elephant chortled")

(str (interleave "Attack at midnight"
                 "The purple elephant chortled"))
(apply str (interleave "Attack at midnight"
                       "The purple elephant chortled"))
(apply str (take-nth 2 "ATthtea cpku raptl em iedlneipghhatn"))

;; Booleans and nil
(if () "We are in Clojure!" "We are in Common Lisp!")
(if 0 "Zero is true" "Zero is false")

(true? true)
(true? "foo")
(false? false)
(false? "foo")
(nil? nil)
(nil? false)
(zero? 0.0)
(zero? (/ 22 7))

;; Maps, Keywords, and Records
(def inventors {"Lisp" "McCarthy" "Clojure" "Hickey"})
(inventors "Lisp")
(inventors "Foo")
(get inventors "Lisp" "I dunno!")
(get inventors "Foo" "I dunno!")

:foo
;; foo Fails
(def inventors {:Lisp "McCarthy" :Clojure "Hickey"})
(inventors :Lisp)
(inventors :Clojure)

(defrecord Book [title author])
(->Book "title" "author")
(def b (->Book "Anathem" "Neal Stephenson"))

b
(:title b)
;; (b :title) fails
(Book. "Anathem" "Neal Stephenson")
#user.Book{:title "Infinite Jest", :author "David Foster Wallace"}

;; Reader Macros
'(1 2)
(quote (1 2))

;; Anonymous function #(.toUpperCase %)
;; Comment ;single-line comment
;; Deref @form => (deref form)
;; Metadata ^metadata form
;; Quote 'form => (quote form)
;; Regex pattern #"foo" => a java.util.regex.Pattern
;; Syntax-quote `x'
;; Unquote ~
;; Unquote-splicing ~@
;; Var-quote #'x => (var x)

;; Functions
;; function calls are lists
(str "hello" " " "world")

(string? "hello")
(keyword? :hello)
(symbol? 'hello)

(defn greeting
  "Returns a greeting of the form 'Hello, username.'
  Default username is 'world'."
  ([] (greeting "world"))
  ([username] (str "Hello, " username)))

(greeting "Peter")
(greeting)

(defn date
  [person-1 person-2 & chaperones]
  (println person-1 "and" person-2
           "went out with" (count chaperones)
           "chaperones."))

(date "Romeo" "Juliet" "Friar Lawrence" "Nurse")

;; Anonymous functions
(defn indexable-word?
  [word]
  (> (count word) 2))

(require '[clojure.string :as str])
(filter indexable-word? (str/split "A fine day it is" #"\W+"))
(filter (fn [w] (> (count w) 2))
        (str/split "A fine day it is" #"\W+"))
(filter #(> (count %) 2) (str/split "A fine day it is" #"\W+"))

(defn indexable-words [text]
  (let [indexable-word? (fn [w] (> (count w) 2))]
    (filter indexable-word? (str/split text #"\W+"))))

(indexable-words "A fine day it is")

(defn make-greeter [greeting-prefix]
  (fn [username]
    (str greeting-prefix ", " username)))

(def hello-greeting (make-greeter "Hello"))
(def aloha-greeting (make-greeter "Aloha"))

(hello-greeting "world")
(aloha-greeting "world")
((make-greeter "Howdy") "pardner")

;; Vars, Bindings and Namespaces
(def foo 10)
foo
(var foo)
#'foo

(defn triple [number]
  (* 3 number))

(triple 10)

(defn square-corners
  [bottom left size]
  (let [top (+ bottom size)
        right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))

(square-corners 3 2 5)

;; Destructuring
(defn greet-author-1 [author]
  (println "Hello, " (:first-name author)))

(greet-author-1 {:last-name "Vinge" :first-name "Vernon"})

(defn greet-author-2 [{fname :first-name}]
  (println "Hello, " fname))

(greet-author-2 {:last-name "Vinge" :first-name "Vernon"})

(let [[x y] [1 2 3]]
  [x y])

(let [[_ _ z] [1 2 3]]
  z)

;; the final bound value is returned
(let [[_ _ z] [1 2 3]]
  _)

(let [[x y :as coords] [1 2 3 4 5 6]]
  (str "x: " x ", y: " y ", total dimensions " (count coords)))

(require '[clojure.string :as str])

(defn ellipsize
  [words]
  (let [[w1 w2 w3] (str/split words #"\s+")]
    (str/join " " [w1 w2 w3 "..."])))

(ellipsize "The quick brown fox jumps over the lazy dog.")

;; Namespaces
(def foo 10)
(resolve 'foo)

(in-ns 'my-app)
String
(clojure.core/use 'clojure.core)
;; File/separator fails
java.io.File/separator

(import '(java.io InputStream File))

(.exists (File. "/tmp"))

(require 'clojure.string)
(clojure.string/split "Something,separated,by,commas" #",")

;; (split "Something,separated,by,commas" #",") fails

(require '[clojure.string :as str])
(str/split "Something,separated,by,commas" #",")

(ns examples.exploring
  (:require [clojure.string :as str])
  (:import (java.io File)))

(in-ns 'user)
(clojure.repl/find-doc "ns-")

;; Calling Java
(new java.util.Random)
(def rnd (new java.util.Random))

(. rnd nextInt)
(. rnd nextInt 10)

(. Math PI)

(import '(java.util Random Locale)
        '(java.text MessageFormat))

Random
Locale
MessageFormat

(javadoc java.net.URL)

;; Flow Control
(defn is-small? [number]
  (if (< number 100) "yes"))

(is-small? 50)
(is-small? 50000)

(defn is-small? [number]
  (if (< number 100) "yes" "no"))

(is-small? 50)
(is-small? 50000)

(defn is-small? [number]
  (if (< number 100)
    "yes"
    (do
      (println "Saw a big number" number)
      "no")))

(is-small? 50)
(is-small? 50000)

(loop [result [] x 5]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))

(defn countdown [result x]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))

(countdown [] 5)

(into [] (take 5 (iterate dec 5)))
(into [] (drop-last (reverse (range 6))))
(vec (reverse (rest (range 6))))

(defn indexed [coll] (map-indexed vector coll))
(indexed "abcde")

(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll)
          :when (pred elt)]
      idx)))

(index-filter #{\a \b} "abcdbbb")
(index-filter #{\a \b} "xyz")

(defn index-of-any [pred coll]
  (first (index-filter pred coll)))

(index-of-any #{\z \a} "zzabyycdxx")
(index-of-any #{\b \y} "zzabyycdxx")
(nth (index-filter #{:h} [:t :t :h :t :h :t :t :t :h :h]) 2)

;; Metadata
(meta #'str)

;; Metadata key | Used For
;; :arglists | Parameter info used by doc
;; :doc | Documentation used by doc
;; :file | Source file
;; :line | Source line number
;; :macro | True for macros
;; :name | Local name
;; :ns | Namespace
;; :tag | Expected argument or return type

(defn ^{:tag String} shout [^{:tag String} s]
  (.toUpperCase s))

(shout "hi")
(meta #'shout)

(defn ^String shout [^String s]
  (.toUpperCase s))
(shout "hi")

(defn shout
  ([s] (.toUpperCase s))
  {:tag String})
(shout "hi")

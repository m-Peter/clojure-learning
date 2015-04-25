(defn blank?
  "A string is blank if every character
  in it is whitespace."
  [str]
  (every? #(Character/isWhitespace %) str))

(= true (blank? ""))
(= true (blank? " "))
(= false (blank? "not"))

(defrecord Person [first-name last-name])

(def foo (->Person "Peter" "Markou"))

;; Specifying function parameters using a vector ([])
(defn hello-word [username]
  (println (format "Hello, %s" username)))

[1, 2, 3, 4]
[1 2 3 4] ;; commas are whitespaces

;; Clojure cond
(def x 10)
(cond (= x 10) "equal"
      (> x 10) "more")

(def compositions
  [{:name "Requiem" :composer "W. A. Mozart"}
   {:name "Requiem" :composer "Giuseppe Verdi"}
   {:name "Dawn" :composer "Richard Strauss"}])
;; Search for composers who have written "Requiem"
(for [c compositions
      :when (= "Requiem" (:name c))]
  (:composer c))

;; Clojure simplifies concurrent programming
(def accounts (ref #{}))
(defrecord Account [id balance])

(dosync
 (alter accounts conj (->Account "CLJ" 1000.00)))

;; Clojure embraces the JVM
(System/getProperties)
(.. "hello" getClass getProtectionDomain)
(.start (new Thread
             (fn []
               (println "Hello" (Thread/currentThread)))))

;; Clojure Coding Quick Start
(println "hello world")

(defn hello [name]
  (str "Hello, " name))

;; (/ 1 0) throws exception

(pst) ;; prints the stacktrace

#{} ;; literal for creating a set
(conj #{} "Stu")

(def visitors (atom #{}))
(swap! visitors conj "Stu")
(deref visitors)
@visitors

(defn hello
  "Writes hello message to *out*. Calls you by username.
  Knows if you have been here before."
  [username]
  (swap! visitors conj username)
  (println "Hello, " username))

;; Exploring Clojure libraries
(require 'clojure.java.io)

(ancestors (class [1 2 3]))

;; Finding documentation
(doc str)
(doc hello)
(find-doc "reduce") ;; searches by string or regex
(source identity) ;; view source of function

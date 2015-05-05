;; Everything Is a Sequence
(first nil)
(first [1])

(rest [])
(rest nil)
(rest [1 2 3])

(cons 1 [])
(cons 1 nil)
(cons 1 [2])

(seq [])
(seq nil)
(seq [1 2 3])

(next [])
(next nil)
(next [1 2 3]) ;; same as (seq (rest [1 2 3]))
(seq (rest [1 2 3]))
(seq (rest [1]))

;; Clarifying rest/next behavior
(rest ())
(next ())
(seq (rest ()))

(first '(1 2 3))
(rest '(1 2 3))
(cons 0 '(1 2 3))

(first [1 2 3])
(rest [1 2 3])
(cons 0 [1 2 3])

(class '(1 2 3))
(class [1 2 3])
(class (rest [1 2 3]))

(first {:fname "Aaron" :lname "Bedra"})
(rest {:fname "Aaron" :lname "Bedra"})
(cons [:mname "Jones"] {:fname "Aaron" :lname "Bedra"})

(first #{:the :quick :brown :fox})
(rest #{:the :quick :brown :fox})
(cons :jumped #{:the :quick :brown :fox})

;; arbitrary traversal
#{:the :quick :brown :fox}

(sorted-set :the :quick :brown :fox)

{:a 1 :b 2 :c 3}

(sorted-map :c 3 :b 2 :a 1)

(conj '(1 2 3) :a)
;; into uses conj
(into '(1 2 3) '(:a :b :c))

(conj [1 2 3] :a)
(into [1 2 3] [:a :b :c])

;; Using the sequence library

;; Creating sequences
(range 10)
(range 10 20)
(range 1 25 2)

(repeat 5 1)
(repeat 10 "x")

(take 10 (iterate inc 1))

(defn whole-numbers [] (iterate inc 1))

(take 20 (repeat 1))

(take 10 (cycle (range 3)))

(interleave (whole-numbers) ["A" "B" "C" "D" "E"])

(interpose "," ["apples" "bananas" "grapes"])
(apply str (interpose \, ["apples" "bananas" "grapes"]))

(use '[clojure.string :only (join)])
(join \, ["apples" "bananas" "grapes"])

(set [1 2 3])
(hash-set 1 2 3)

(vec (range 3))
(vector 0 1 2)

(take 10 (filter even? (whole-numbers)))
(take 10 (filter odd? (whole-numbers)))

(take-while (complement #{\a \e \i \o \u})
            "the quick brown fox")

(drop-while (complement #{\a \e \i \o \u})
            "the quick brown fox")

(split-at 5 (range 10))
(split-with #(<= % 10) (range 0 20 2))

(every? odd? [1 3 5])
(every? odd? [1 3 5 8])

(some even? [1 2 3])
(some even? [1 3 5])

(some identity [nil false 1 nil 2])

(not-every? even? (whole-numbers))

(not-any? even? (whole-numbers))

(map #(format "<p>%s</p>" %) ["the" "quick" "brown" "fox"])
(map #(format "<%s>%s</%s>" %1 %2 %1)
     ["h1" "h2" "h3" "h1"]
     ["the" "quick" "brown" "fox"])

(reduce + (range 1 11))
(reduce * (range 1 11))

(sort [42 1 7 11])

(sort-by #(.toString %) [42 1 7 11])

(sort > [42 1 7 11])

(sort-by :grade > [{:grade 83} {:grade 90} {:grade 77}])

(for [word ["the" "quick" "brown" "fox"]]
  (format "<p>%s</p>" word))

(take 10 (for [n (whole-numbers)
               :when (even? n)]
           n))

(for [n (whole-numbers)
      :while (even? n)]
  n)

(for [file "ABCDEFGH"
      rank (range 1 9)]
  (format "%c%d" file rank))

(for [rank (range 1 9)
      file "ABCDEFGH"]
  (format "%c%d" file rank))

(def primes
  (concat
   [2 3 5 7]
   (lazy-seq
    (let [primes-from
          (fn primes-from [n [f & r]]
            (if (some #(zero? (rem n %))
                      (take-while #(<= (* % %) n) primes))
              (recur (+ n f) r)
              (lazy-seq
               (cons n (primes-from (+ n f) r)))))
          wheel (cycle [2 4 2 4 6 2 6 4 2 4 6 6 2 6 4 2
                        6 4 6 8 4 2 4 2 4 8 6 4 6 2 4 6
                        2 6 6 4 2 4 6 2 6 4 2 4 2 10 2 10])]
      (primes-from 11 wheel)))))

(def ordinals-and-primes (map vector (iterate inc 1) primes))

(take 5 (drop 1000 ordinals-and-primes))
(take 3 (range))

(def x (for [i (range 1 3)]
         (do (println i) i)))

(doall x)
(dorun x)

;; Seq-able collections

(first (.getBytes "hello"))
(rest (.getBytes "hello"))
(cons (int \h) (.getBytes "ello"))

(first (System/getProperties))
(rest (System/getProperties))

(first "Hello")
(rest "Hello")
(cons \H "ello")

(reverse "Hello")
(apply str (reverse "Hello"))

;; don't do this
(let [m (re-matcher #"\w+" "the quick brown fox")]
  (loop [match (re-find m)]
    (when match
      (println match)
      (recur (re-find m)))))

(re-seq #"\w+" "the quick brown fox")
(sort (re-seq #"\w+" "the quick brown fox"))
(drop 2 (re-seq #"\w+" "the quick brown fox"))
(map #(.toUpperCase %) (re-seq #"\w+" "the quick brown fox"))

(import '(java.io File))
(.listFiles (File. "."))

(seq (.listFiles (File. ".")))

(map #(.getName %) (seq (.listFiles (File. "."))))
(map #(.getName %) (.listFiles (File. ".")))

(count (file-seq (File. ".")))

(defn minutes-to-millis
  [mins]
  (* mins 1000 60))

(defn recently-modified?
  [file]
  (> (.lastModified file)
     (- (System/currentTimeMillis) (minutes-to-millis 30))))

(filter recently-modified? (file-seq (File. ".")))

(use '[clojure.java.io :only (reader)])
(take 2 (line-seq (reader "chapter-3.clj")))

(with-open [rdr (reader "chapter-3.clj")]
  (count (line-seq rdr)))

(with-open [rdr (reader "chapter-3.clj")]
  (count (filter #(re-find #"\S" %) (line-seq rdr))))

(defn non-blank? [line]
  (if (re-find #"\S" line)
    true
    false))

(defn non-svn? [file]
  (not (.contains (.toString file) ".svn")))

(defn clojure-source? [file]
  (.endsWith (.toString file) ".clj"))

(defn clojure-loc [base-file]
  (reduce
   +
   (for [file (file-seq base-file)
         :when (and (clojure-source? file)
                    (non-svn? file))]
     (with-open [rdr (reader file)]
       (count (filter non-blank? (line-seq rdr)))))))

(clojure-loc (java.io.File. "/home/mpeter/Documents/clojure-learning/programming-clojure"))

(use '[clojure.xml :only (parse)])

(parse (java.io.File. "compositions.xml"))

(for [x (xml-seq
         (parse (java.io.File. "compositions.xml")))
      :when (= :composition (:tag x))]
  (:composer (:attrs x)))

;; Functions on Lists
(peek '(1 2 3))
(pop '(1 2 3))

(rest ())
;; (pop ()) throws Exception

;; Functions on Vectors
(peek [1 2 3])
(pop [1 2 3])

(get [:a :b :c] 1)
(get [:a :b :c] 5)

([:a :b :c] 1)
;; ([:a :b :c] 5) throws Exception if index is invalid

(assoc [0 1 2 3 4] 2 :two)

(subvec [1 2 3 4 5] 3)
(subvec [1 2 3 4 5] 1 3)
(take 2 (drop 1 [1 2 3 4 5]))

;; Functions on Maps
(keys {:sundance "spaniel" :darwin "beagle"})
(vals {:sundance "spaniel" :darwin "beagle"})

(get {:sundance "spaniel" :darwin "beagle"} :darwin)
(get {:sundance "spaniel" :darwin "beagle"} :snoopy)

({:sundance "spaniel" :darwin "beagle"} :darwin)
({:sundane "spaniel" :darwin "beagle"} :snoopy)

(:darwin {:sundance "spaniel" :darwin "beagle"})
(:snoopy {:sundance "spaniel" :darwin "beagle"})

(def score {:stu nil :joey 100})

(:stu score)
(contains? score :stu)

(get score :stu :score-not-found)
(get score :aaron :score-not-found)

(def song {:name "Agnus Dei"
           :artist "Krzysztof Penderecki"
           :album "Polish Requiem"
           :genre "Classical"})

(assoc song :kind "MPEG Audio File")
(dissoc song :genre)
(select-keys song [:name :artist])
(merge song {:size 8118166, :time 507245})
(merge-with
 concat
 {:rubble ["Barney"], :flinstone ["Fred"]}
 {:rubble ["Betty"], :flinstone ["Wilma"]}
 {:rubble ["Baum-Bam"], :flinstone ["Bam-Bam"]})

;; Functions on Sets
(require 'clojure.set)

(def languages #{"java" "c" "d" "clojure"})
(def beverages #{"java" "chai" "pop"})

(clojure.set/union languages beverages)
(clojure.set/difference languages beverages)
(clojure.set/intersection languages beverages)
(clojure.set/select #(= 1 (.length %)) languages)

(def compositions
  #{{:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}})
(def composers
  #{{:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}})
(def nations
  #{{:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}})

(clojure.set/rename compositions {:name :title})
(clojure.set/select #(= "Requiem" (:name %)) compositions)
(clojure.set/project compositions [:name])
(for [m compositions
      c composers]
  (concat m c))
(clojure.set/join compositions composers)
(clojure.set/join composers nations {:country :nation})

(def requiems
  (clojure.set/select #(= (:name %) "Requiem") compositions))

(def composers
  (clojure.set/join composers requiems))

(def countries
  (clojure.set/project composers [:country]))

(clojure.set/project
 (clojure.set/join composers
                   (clojure.set/select
                    #(= (:name %) "Requiem") compositions))
 [:country])

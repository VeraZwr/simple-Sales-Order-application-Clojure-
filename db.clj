(ns myproject.db
   (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def custDB [])
(def prodDB [])
(def salesDB [])
(def custname [])
(def prodname [])

(def data-file (io/resource "cust.txt"))
(def data-file2 (io/resource "prod.txt"))
(def data-file3 (io/resource "sales.txt"))
(def cust(slurp data-file))
(def prod(slurp data-file2))
(def sales(slurp data-file3))


(defn element-separate [s]
  (def str1 (clojure.string/split-lines s))
  (def DB [])
  (doseq [x str1]
    (def str2 (str/split x #"\|"))
    (def DB (conj DB str2 ))
   );(println DB)  
  DB
 )

;(element-separate cust)

(defn readFile []
 (def custDB (element-separate cust))
 ;(println custDB)
 (def prodDB (element-separate prod))
 ;(println prodDB)
 (def salesDB (element-separate sales))
 ;(println salesDB)
)
;(readFile);show nil here

(defn showMenu [] (println "*** Sales Menu ***\n------------------\n\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4.Total Sales Table\n5.Total Count for Product\n6.Exit\n"))
;(showMenu)

(defn option1 []
 (doseq [x custDB] (println (first x) ": " (rest x)))
)

(defn option2 []
 (doseq [x prodDB] (println (first x) ": " (rest x)))
)

(defn option3 []
  (doseq [x salesDB] 
    (println (get x 0) ":" 
             (get (get custDB (- (Integer/parseInt(get x 1)) 1)) 1)" "
             (get (get prodDB (- (Integer/parseInt(get x 2)) 1)) 1) " "
             (get x 3)
    )
   )
)
(defn getcustname[]
  (doseq [x custDB] 
    (def nametmp (get x 1))
    (def custname (conj custname nametmp))
   )
  custname
 )

(defn custsum [s4]
  (def custindex (+ (.indexOf custname s4) 1))
  (println custindex)
  (def sumprice 0.0)
  (doseq [x salesDB] 
   ; (println "in the doseq statment")
    (if(= custindex (Integer/parseInt(get x 1)))
      (do
        ;(println "in the if statment")
        (def quant (Float/parseFloat(get x 3)))
       ; (println quant)
                                   ;(price (prod(index of prod)))
        (def price (Float/parseFloat(get (get prodDB (- (Integer/parseInt(get x 2)) 1)) 2)))
        ;(println price)
        (def sumpricetemp (* quant price))
        ;(println sumpricetemp)
       
      (def sumprice (+ sumprice sumpricetemp))
      )
    )
  )
  sumprice
)

(defn option4 []
  (getcustname)
  (println  "Enter a customer name ") 
  (def inputname (read-line))
  (println inputname)
    (if(some #(= inputname %) custname)
      (println inputname ":" (format "%.2f" (custsum inputname))"$")
      
      (do
        (println "Customer does not exist")
        (option4)
       )
     )
) 

(defn getprodname[]
  ;(println prodDB)
  (doseq [x prodDB] 
    (def prodtmp (get x 1))
    (def prodname (conj prodname prodtmp))
   )
   prodname
 )

(defn prodsum [s5]
  (def prodindex (+ (.indexOf prodname s5) 1))
  (println prodindex)
  (def totalprod 0)
  (doseq [x salesDB] 
   ; (println "in the doseq statment")
    (if(= prodindex (Integer/parseInt(get x 2)))
       (def totalprod (+ totalprod (Integer/parseInt(get x 3))))
    )
  )
  totalprod
)

(defn option5 []
  (getprodname)
  (println  "Enter a product name ") 
  (def inputprod (read-line))
  (println inputprod)
    (if(some #(= inputprod %) prodname)
      (println inputprod ":"  (prodsum inputprod))  
      (do
        (println "Product does not exist")
        (option5)
       )
     )
) 
; (getprodname) 
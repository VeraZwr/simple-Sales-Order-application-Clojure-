(ns myproject.menu
   (:require [myproject.db :as db]))
(defn option []
  (db/readFile)
  (db/showMenu);cannot show menu here
  (println  "Enter an option?") 
  (def options (read-line))
  ; (loop [options (read-line)]
    (cond 
    (= "6" options) (println "Good Bye")
    (= "1" options) (do(db/option1)(option))
    (= "2" options) (do(db/option2)(option))
    (= "3" options) (do(db/option3)(option))
    (= "4" options) (do(db/option4)(option))
    (= "5" options) (do(db/option5)(option))
    :else (do(println "Error")(option))
    )
  ;)
)
(option)


(ns brainfuck-clj.core
    (:require [brainfuck-cljs.engine :refer [bf-interpreter]])
    (:gen-class))


(defn -main [& args]
  (if (nth args 0)
    (bf-interpreter (slurp (nth args 0)) #(.read System/in))
    (println "Please specify a brainfuck file as the first argument")))

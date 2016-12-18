(ns brainfuck-clj.engine
 (:require [cljs.tools.reader.reader-types :as rt]))

(defn bf-interpreter [program-code input-reader]
  (let [find-bracket (fn [opening-bracket closing-bracket instruction-pointer direction]
                       (loop [i (direction instruction-pointer) opened 0]
                         (condp = (nth program-code i)
                           opening-bracket (recur (direction i) (inc opened))
                           closing-bracket (if (zero? opened) i (recur (direction i) (dec opened)))
                           (recur (direction i) opened))))
        fdec (fn [n] (max 0 (dec n)))]

    (loop [cells [0N] current-cell 0 instruction-pointer 0]
      (condp = (get program-code instruction-pointer)
        \<  (recur cells (fdec current-cell) (inc instruction-pointer))
        \+  (recur (update cells current-cell inc) current-cell (inc instruction-pointer))
        \-  (recur (update cells current-cell fdec) current-cell (inc instruction-pointer))
        \>  (let [next-ptr (inc current-cell)
                  next-cells (if (= next-ptr (count cells)) 
                               (conj cells 0N) 
                               cells)]
              (recur next-cells next-ptr (inc instruction-pointer)))
        \.  (do
              (print (char (nth cells current-cell)))
              (recur cells current-cell (inc instruction-pointer)))
        \,  (let [ch (input-reader)]
              (recur (assoc cells current-cell ch) current-cell (inc instruction-pointer)))
        \[  (recur cells current-cell (inc (if (zero? (nth cells current-cell))
                                             (find-bracket \[ \] instruction-pointer inc)
                                             instruction-pointer)))
        \]  (recur cells current-cell (find-bracket \] \[ instruction-pointer dec))
        nil cells ; end of the program
        (recur cells current-cell (inc instruction-pointer))))))

#?(:cljs
(defn bf [program-code input-str]
  (let [reader (rt/string-reader input-str)]
	(bf-interpreter program-code  #(rt/read-char reader)))))

(ns brainfuck-clj.core
    (:gen-class))

(defn bf-interpreter [program-code]
    (let [
        find-bracket (fn [opening-bracket closing-bracket instruction-pointer direction]
            (loop [i (direction instruction-pointer) opened 0]
                (condp = (nth program-code i)
                    opening-bracket (recur (direction i) (inc opened))
                    closing-bracket (if (zero? opened) i (recur (direction i) (dec opened)))
                    (recur (direction i) opened))))]

        (loop [cells [0N], current-cell 0, instruction-pointer 0]
            (condp = (get program-code instruction-pointer)
                \<  (recur cells (dec current-cell) (inc instruction-pointer))
                \+  (recur (update-in cells [current-cell] inc) current-cell (inc instruction-pointer))
                \-  (recur (update-in cells [current-cell] dec) current-cell (inc instruction-pointer))

                \>  (let [
                            next-ptr (inc current-cell)
                            next-cells (if (= next-ptr (count cells)) 
                                            (conj cells 0N) 
                                             cells)]
                        (recur next-cells next-ptr (inc instruction-pointer)))

                \.  (do
                        (print (char (nth cells current-cell)))
                        (recur cells current-cell (inc instruction-pointer)))
                \,  (let [ch (.read System/in)]
                        (recur (assoc cells current-cell ch) current-cell (inc instruction-pointer)))
                \[  (recur cells current-cell (inc (if (zero? (nth cells current-cell))
                        (find-bracket \[ \] instruction-pointer inc)
                        instruction-pointer)))
                \]  (recur cells current-cell (find-bracket \] \[ instruction-pointer dec))
                nil cells
                (recur cells current-cell (inc instruction-pointer))))))

(defn -main [& args]
  (if (nth args 0)
    (bf-interpreter (slurp (nth args 0)))
    (println "Please specify a brainfuck file as the first argument")))

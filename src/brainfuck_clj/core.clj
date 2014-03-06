(ns brainfuck-clj.core
    (:gen-class))

(defn bf-interpreter [& code-lines]
    (let [
        program-code (apply str code-lines)

        find-bracket (fn [opening-bracket closing-bracket ip direction]
            (loop [i (direction ip) opened 0]
                (condp = (nth program-code i)
                    opening-bracket (recur (direction i) (inc opened))
                    closing-bracket (if (zero? opened) i (recur (direction i) (dec opened)))
                    (recur (direction i) opened))))]

        (loop [cells [0N], current-cell 0, ip 0]
            (condp = (get program-code ip)
                \<  (recur cells (dec current-cell) (inc ip))
                \+  (recur (update-in cells [current-cell] inc) current-cell (inc ip))
                \-  (recur (update-in cells [current-cell] dec) current-cell (inc ip))

                \>  (let [next-ptr (inc current-cell)
                            next-cells (if (= next-ptr (count cells)) (conj cells 0N) cells)]
                        (recur next-cells next-ptr (inc ip)))

                \.  (do
                        (print (char (nth cells current-cell)))
                        (recur cells current-cell (inc ip)))
                \,  (let [ch (.read System/in)]
                        (recur (assoc cells current-cell ch) current-cell (inc ip)))
                \[  (recur cells current-cell (inc (if (zero? (nth cells current-cell))
                        (find-bracket \[ \] ip inc)
                        ip)))
                \]  (recur cells current-cell (find-bracket \] \[ ip dec))
                nil cells
                (recur cells current-cell (inc ip))))))

(defn -main [& args]
  (if (nth args 0)
      (bf-interpreter (slurp (nth args 0)))
    (println "Please specify a brainfuck file as the first argument")))

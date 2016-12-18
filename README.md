# brainfuck-clj

A Brainfuck interpreter in Clojure. As simple as that.

## Live Demo

Here is [live demo in the browser](http://app.klipse.tech/?cljs_in=(require%20%27%5Bbrainfuck-clj.engine%20%3Arefer%20%5Bbf%5D%5D)%0A%0A%5B(bf%20%22%2C.%2C.%22%20%22abc%22)%0A%20(bf%20%22%2B%2B%2B%2B%3E%2B%2B%2B%3E%2B%2B%2B%2B%2B%2B%22)%5D%0A&eval_only=1&external-libs=%5Bhttps%3A%2F%2Fraw.githubusercontent.com%2Fviebel%2Fbrainfuck-clj%2Fmaster%2Fsrc%2F%5D).
## Usage

```
lein run my_awesome_brainfuck_program.b
```

You can find a few samples in the directory brainfuck-samples, e.g. to get all square numbers from 0 to 10000 type:

```
lein run brainfuck-samples/squares.b 
```


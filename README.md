# ExprEvaluator
*A lightweight, self-contained expression parser & evaluator implemented in pure Java using functional parser-combinator techniques.*

---

## ✨ Features
- **Parser-Combinator Core** – `parser.*` provides `Parser<T>`, combinators (`map`, `filter`, `zip`, `optional`, `zeroOrMore`, …) and utility parsers.
- **Arithmetic Grammar** – `grammar.ExpressionGrammar` recognises integers, floats, addition, subtraction, multiplication, and division with correct precedence.
- **Modern AST** – Built with Java 17 sealed interfaces & records (`ast.AST`) for strong exhaustiveness checks.
- **Evaluation Engine** – `eval.Eval` traverses the AST and produces `Long` results (multiplication/division coming soon).
- **Zero Dependencies** – Compiles and runs with nothing but a JDK 24+.
- **Self-Contained Tests** – `ExprEvaluator.main()` performs assertion-based smoke tests and prints *All Test Successful* on success.

---

## 🗂️ Directory Layout
```
└── src
    ├── ast                            # Sealed interfaces & records representing the syntax tree
    ├── eval                           # AST Evaluation Logic
    ├── ExprEvaluator.java
    ├── grammar                        # Grammar built via Parser combinator
    ├── model
    ├── parser                         # Fully Functional Parser Library from Scratch
    ├── repl
    └── test
```

---

## ⏳ Requirements
* **Java 24** or newer (for records, sealed interfaces, and pattern matching).

---

## Example Expression

- `-1 + ((1 - 2) * 3) + 4.0 / 2` => `-2.0D`

---

## 🔧 Building & Running
- Running the tests
```sh
   java -ea src/ExprEvaluator.java
```
- Running REPL
```sh
   java src/ExprEvaluator.java -r
   # or directly
   ./src/ExprEvaluator.java -r
```

---

## 🛠️ How It Works
1. **Parser-Combinator Library**
   The generic interface `Parser<T>` exposes `parse(String)` returning an `Optional<ParseResult<T>>`.
   Core combinators (`map`, `filter`, `zeroOrMore`, `oneOrMore`, etc.) allow composition of complex parsers in a functional style.

2. **Grammar Construction** (`ExpressionGrammar`)
   ```
   number  → integer | float
   primary → number | "(" expr ")"
   factor  → "+" factor | "-" factor | primary
   term    → factor (('*'|'/') factor)*
   expr    → term   (('+'|'-') term)*
   ```
   The grammar is encoded declaratively by composing smaller parsers with combinators like `zip`, `optional`, and `oneOf`.

3. **AST Representation** (`ast.AST`)
   Uses sealed interfaces (`AST.Num`) and records (`Expr`, `Term`, `Factor`) ensuring exhaustiveness in `switch` statements.

4. **Evaluation** (`eval.Eval`)
   Traverses the AST to compute results. Addition/Subtraction for integers is implemented; multiplication/division and floating-point support are planned.

---

## 🗺️ Roadmap / TODO
- [x] Implement `MULT` / `DIV` evaluation and float maths
- [x] Add parentheses and unary operator support
- [x] Provide interactive REPL
- [x] Handle Spaces 

---

## 🙏 Acknowledgements
Inspired by functional parser-combinator libraries in Haskell & Scala.

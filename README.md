# ExprEvaluator
*A lightweight, self-contained expression parser & evaluator implemented in pure Java 17 using functional parser-combinator techniques.*

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
src/
  ast/        └─ AST.java        # Sealed interfaces & records representing the syntax tree
  eval/       └─ Eval.java       # AST evaluation logic
  grammar/    └─ ExpressionGrammar.java   # Grammar built via combinators
  parser/     ├─ Parser.java
              ├─ ParseResult.java
              ├─ ParserCombinators.java
              ├─ Parsers.java
              ├─ Pair.java
              └─ ParseFunction.java
  test/       # (placeholder for future JUnit tests)
  ExprEvaluator.java  # Demo entry-point executing assertions
```

---

## ⏳ Requirements
* **Java 24** or newer (for records, sealed interfaces, and pattern matching).

---

## 🔧 Building & Running
```bash
java -ea ExprEvaluator.java
```

---

## 🛠️ How It Works
1. **Parser-Combinator Library**  
   The generic interface `Parser<T>` exposes `parse(String)` returning an `Optional<ParseResult<T>>`.  
   Core combinators (`map`, `filter`, `zeroOrMore`, `oneOrMore`, etc.) allow composition of complex parsers in a functional style.

2. **Grammar Construction** (`ExpressionGrammar`)  
   ```
   number  → integer | float
   factor  → number
   term    → factor (('*'|'/') factor)*
   expr    → term   (('+'|'-') term)*
   ```
   The grammar is encoded declaratively by composing smaller parsers with combinators like `zip`, `optional`, and `oneOf`.

3. **AST Representation** (`ast.AST`)  
   Uses sealed interfaces (`AST.Num`) and records (`Expr`, `Term`, `Factor`) ensuring exhaustiveness in `switch` statements.

4. **Evaluation** (`eval.Eval`)  
   Traverses the AST to compute results. Addition/Subtraction for integers is implemented; multiplication/division and floating-point support are planned.

---

## 🚀 Extending the Project
| What to add            | Where to change                          |
|------------------------|------------------------------------------|
| Multiplication/division evaluation | `eval.Eval::eval(AST.Term)` |
| Parentheses handling   | Add rule to `ExpressionGrammar` & update AST |
| Unary plus/minus       | Extend grammar and evaluator            |
| CLI / REPL             | New class wrapping parser & evaluator   |
| Unit tests             | Add JUnit tests under `src/test`        |

---

## 🗺️ Roadmap / TODO
- [ ] Implement `MULT` / `DIV` evaluation and float maths  
- [ ] Add parentheses and unary operator support  
- [ ] Provide interactive REPL
- [ ] Replace inline assertions with structured JUnit tests  

---

## 📄 License
Released under the MIT License – see [LICENSE](LICENSE) for details *(file not yet included).*

---

## 🙏 Acknowledgements
Inspired by functional parser-combinator libraries in Haskell & Scala.

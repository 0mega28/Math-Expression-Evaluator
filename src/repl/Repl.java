package repl;

import static java.lang.System.console;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import model.Result;
import grammar.ExpressionGrammar;

public interface Repl {

    static Result<String, String> handleExpression(String expr) {
        return ExpressionGrammar.expr.parse(expr)
                .<Result<String, String>>map(ast -> {
                    if (!ast.remaining().isEmpty()) {
                        return Result.err("Invalid Expression: " + ast.remaining());
                    }

                    var value = eval.Eval.eval(ast.value());
                    return Result.ok(value.toString());
                })
                .orElseGet(() -> Result.err("Invalid Expression: " + expr));
    }

    static void handleOutput(Result<String, String> result) {
        switch (result) {
            case Result.Ok(var value) -> {
                System.out.println(value);
            }
            case Result.Err(var error) -> {
                System.err.println(error);
            }
        }
    }

    static void repl() {
        Stream.generate(() -> console().readLine("expr> "))
                .takeWhile(Predicate.not(Objects::isNull))
                .filter(Predicate.not(Objects::isNull))
                .map(String::trim)
                .takeWhile(Predicate.not("quit"::equals))
                .map(Repl::handleExpression)
                .forEach(Repl::handleOutput);

        System.out.println("Bye 👋🏻");
    }
}

package eval;

import ast.AST;
import ast.AST.Factor;

public interface Eval {
    static Long eval(AST.Expr expr) {
        var init = eval(expr.term());

        for (var rest: expr.rest()) {
            AST.AddSub addOrSub = rest.first();
            var value = eval(rest.second());
            
            init = switch(addOrSub) {
                case AST.AddSub.ADD -> init + value;
                case AST.AddSub.SUB -> init - value;
            };
        }

        return init;
    }

    static Long eval(AST.Term term) {
        var init = eval(term.factor());

        for (var rest: term.rest()) {
            throw new UnsupportedOperationException();
        }

        return init;
    }

    static Long eval(Factor factor) {
        AST.Num value = factor.value();
        return switch(value) {
            case AST.Num.Int(var i) -> i;
            case AST.Num.Float(var _f) -> throw new UnsupportedOperationException();
        };
    }
}

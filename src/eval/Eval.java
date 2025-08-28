package eval;

import ast.AST;
import ast.AST.Factor;
import model.AddSub;
import model.MultDiv;
import model.Num;

public interface Eval {
    static Long eval(AST.Expr expr) {
        var init = eval(expr.term());

        for (var rest: expr.rest()) {
            AddSub addOrSub = rest.first();
            var value = eval(rest.second());
            
            init = switch(addOrSub) {
                case AddSub.ADD -> init + value;
                case AddSub.SUB -> init - value;
            };
        }

        return init;
    }

    static Long eval(AST.Term term) {
        var init = eval(term.factor());

        for (var rest: term.rest()) {
            MultDiv multOrDiv = rest.first();
            var value = eval(rest.second());

            init = switch(multOrDiv) {
                case MultDiv.MULT -> init * value;
                case MultDiv.DIV -> init / value;
            };
        }

        return init;
    }

    static Long eval(Factor factor) {
        Num value = factor.value();
        return switch(value) {
            case Num.Int(var i) -> i;
            case Num.Float(var _f) -> throw new UnsupportedOperationException();
        };
    }
}

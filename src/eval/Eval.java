package eval;

import ast.AST;
import ast.AST.Factor;
import model.AddSub;
import model.MultDiv;
import model.Num;

// TODO handle Num.Float
public interface Eval {
    static Long eval(AST.Expr expr) {
        var init = eval(expr.term());

        for (var rest : expr.rest()) {
            AddSub addOrSub = rest.first();
            var value = eval(rest.second());

            init = switch (addOrSub) {
                case AddSub.ADD -> init + value;
                case AddSub.SUB -> init - value;
            };
        }

        return init;
    }

    static Long eval(AST.Term term) {
        var init = eval(term.factor());

        for (var rest : term.rest()) {
            MultDiv multOrDiv = rest.first();
            var value = eval(rest.second());

            init = switch (multOrDiv) {
                case MultDiv.MULT -> init * value;
                case MultDiv.DIV -> init / value;
            };
        }

        return init;
    }

    static Long eval(AST.Factor factor) {
        return switch (factor) {
            case AST.Factor.SignedFactor(AddSub sign, var _factor) -> switch (sign) {
                case AddSub.ADD -> eval(_factor);
                case AddSub.SUB -> -1 * eval(_factor);
            };
            case Factor.Primary(var primary) -> eval(primary);
        };
    }

    static Long eval(model.Num num) {
        return switch (num) {
            case Num.Int(var i) -> i;
            case Num.Float(var _f) -> throw new UnsupportedOperationException();
        };
    }

    static Long eval(AST.Primary primary) {
        return switch (primary) {
            case AST.Primary.Num(var num) -> eval(num);
            case AST.Primary.Expr(var expr) -> eval(expr);
        };
    }
}

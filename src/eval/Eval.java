package eval;

import ast.AST;
import ast.AST.Factor;
import model.AddSub;
import model.MultDiv;
import model.Num;
import static model.Num.BinaryOperator.*;
import static model.Num.UnaryOperator.*;

public interface Eval {
    static Num eval(AST.Expr expr) {
        var result = eval(expr.term());

        for (var rest : expr.rest()) {
            AddSub addOrSub = rest.first();
            var value = eval(rest.second());

            result = switch (addOrSub) {
                case AddSub.ADD -> add.apply(result, value);
                case AddSub.SUB -> sub.apply(result, value);
            };
        }

        return result;
    }

    static Num eval(AST.Term term) {
        var result = eval(term.factor());

        for (var rest : term.rest()) {
            MultDiv multOrDiv = rest.first();
            var value = eval(rest.second());

            result = switch (multOrDiv) {
                case MultDiv.MULT -> mult.apply(result, value);
                case MultDiv.DIV -> div.apply(result, value);
            };
        }

        return result;
    }

    static Num eval(AST.Factor factor) {
        return switch (factor) {
            case AST.Factor.SignedFactor(AddSub sign, var _factor) -> switch (sign) {
                case AddSub.ADD -> eval(_factor);
                case AddSub.SUB -> negate.apply(eval(_factor));
            };
            case Factor.Primary(var primary) -> eval(primary);
        };
    }

    static Num eval(model.Num num) {
        return num;
    }

    static Num eval(AST.Primary primary) {
        return switch (primary) {
            case AST.Primary.Num(var num) -> eval(num);
            case AST.Primary.Expr(var expr) -> eval(expr);
        };
    }
}

package ast;

import java.util.List;

import model.AddSub;
import model.MultDiv;
import parser.Pair;

/* AST from https://stackoverflow.com/questions/34603011/grammar-rule-for-math-expressions-no-left-recursion */
public interface AST {

    record Term(Factor factor, List<Pair<MultDiv, Factor>> rest) {
    }

    sealed interface Factor {
        record SignedFactor(AddSub sign, Factor value) implements Factor {
        }

        record Primary(AST.Primary value) implements Factor {
        }
    }

    record Expr(Term term, List<Pair<AddSub, Term>> rest) {
    }

    sealed interface Primary {
        record Num(model.Num value) implements Primary {
        }

        /* "(" Expr ")" */
        record Expr(AST.Expr value) implements Primary {
        }
    }
}

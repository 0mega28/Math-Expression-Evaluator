package ast;

import java.util.List;

import parser.Pair;

public interface AST {
    sealed interface Num {
        record Int(Long value) implements Num {
        }

        record Float(Double value) implements Num {
        }
    }

    enum AddSub {
        ADD, SUB
    }

    enum MultDiv {
        MULT, DIV
    }

    record Term(Factor factor, List<Pair<MultDiv, Factor>> rest) {
    }

    record Factor(AST.Num value) {
    }

    record Expr(Term term, List<Pair<AddSub, Term>> rest) {
    }
}

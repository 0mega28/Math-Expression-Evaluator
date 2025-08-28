package ast;

import java.util.List;

import model.AddSub;
import model.MultDiv;
import model.Num;
import parser.Pair;

public interface AST {

    record Term(Factor factor, List<Pair<MultDiv, Factor>> rest) {
    }

    record Factor(Num value) {
    }

    record Expr(Term term, List<Pair<AddSub, Term>> rest) {
    }
}

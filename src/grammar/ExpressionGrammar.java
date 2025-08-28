package grammar;

import static parser.ParserCombinators.*;
import static parser.Parsers.*;

import java.util.List;

import ast.AST;
import model.AddSub;
import model.MultDiv;
import model.Num;
import parser.Pair;
import parser.Parser;

public interface ExpressionGrammar {
        @SuppressWarnings("unchecked")
        final static Parser<Num> number = oneOf(
                        floatParser().map(Num.Float::new),
                        integerParser().map(Num.Int::new));

        final static Parser<AST.Factor> factor = number.map(AST.Factor::new);

        @SuppressWarnings("unchecked")
        final static Parser<MultDiv> multOrDiv = oneOf(
                        consume("*").map(_ -> MultDiv.MULT),
                        consume("/").map(_ -> MultDiv.DIV));

        @SuppressWarnings("unchecked")
        final static Parser<AddSub> addOrSub = oneOf(
                        consume("+").map(_ -> AddSub.ADD),
                        consume("-").map(_ -> AddSub.SUB));

        final static Parser<List<Pair<MultDiv, AST.Factor>>> restTerm = zip(multOrDiv, factor).zeroOrMore();
        final static Parser<AST.Term> term = zip(factor, restTerm)
                        .map(value -> new AST.Term(value.first(), value.second()));

        final static Parser<List<Pair<AddSub, AST.Term>>> restExpr = zip(addOrSub, term).zeroOrMore();
        final static Parser<AST.Expr> expr = zip(term, restExpr)
                        .map(value -> new AST.Expr(value.first(), value.second()));
}

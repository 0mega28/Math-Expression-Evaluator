package grammar;

import static parser.ParserCombinators.*;
import static parser.Parsers.*;

import java.util.List;

import ast.AST;
import parser.Pair;
import parser.Parser;

public interface ExpressionGrammar {
    @SuppressWarnings("unchecked")
    final static Parser<AST.Num> number = oneOf(
            floatParser().map(AST.Num.Float::new),
            integerParser().map(AST.Num.Int::new));

    final static Parser<AST.Factor> factor = number.map(AST.Factor::new);

    @SuppressWarnings("unchecked")
    final static Parser<AST.MultDiv> multOrDiv = oneOf(
            consume("*").map(_ -> AST.MultDiv.MULT),
            consume("/").map(_ -> AST.MultDiv.DIV));

    @SuppressWarnings("unchecked")
    final static Parser<AST.AddSub> addOrSub = oneOf(
            consume("+").map(_ -> AST.AddSub.ADD),
            consume("-").map(_ -> AST.AddSub.SUB));

    final static Parser<List<Pair<AST.MultDiv, AST.Factor>>> restTerm = zip(multOrDiv, factor).zeroOrMore();
    final static Parser<AST.Term> term = zip(factor, restTerm)
            .map(value -> new AST.Term(value.first(), value.second()));

    final static Parser<List<Pair<AST.AddSub, AST.Term>>> restExpr = zip(addOrSub, term).zeroOrMore();
    final static Parser<AST.Expr> expr = zip(term, restExpr)
            .map(value -> new AST.Expr(value.first(), value.second()));
}

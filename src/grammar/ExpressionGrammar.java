package grammar;

import static parser.ParserCombinators.*;
import static parser.Parsers.*;

import ast.AST;
import parser.Parser;

public interface ExpressionGrammar {
    @SuppressWarnings("unchecked")
    static Parser<AST.Num> number = oneOf(
            floatParser().map(AST.Num.Float::new),
            integerParser().map(AST.Num.Int::new));

}
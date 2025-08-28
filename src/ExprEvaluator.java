
import java.util.List;
import java.util.Optional;

import grammar.ExpressionGrammar;
import model.Num;

import static parser.ParserCombinators.*;
import static parser.Parsers.*;
import parser.Pair;
import parser.ParseResult;
import parser.Parser;

public class ExprEvaluator {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        parserTest();
        combinatorTest();
        expressionGrammarTest();
        System.out.println("All Test Successful");
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------PARSER-TEST-START-------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------
    static void stringParserTest() {
        Parser<Void> parser = consume("hello");

        assert parser.parse("").equals(Optional.empty());
        assert parser.parse("hell").equals(Optional.empty());
        assert parser.parse("hello").equals(Optional.of(new ParseResult<>(null, "")));
        assert parser.parse("helloo").equals(Optional.of(new ParseResult<>(null, "o")));
    }

    static void charParserTest() {
        Parser<Character> parser = charParser();

        assert parser.parse("").equals(Optional.empty());
        assert parser.parse("a").equals(Optional.of(new ParseResult<>('a', "")));
        assert parser.parse("aa").equals(Optional.of(new ParseResult<>('a', "a")));
    }

    static void filterAndMapTest() {
        Parser<Character> parser = charParser().filter(Character::isDigit);
        var singleDigitParser = parser.map(String::valueOf).map(Integer::parseInt);

        assert parser.parse("a").equals(Optional.empty());
        assert parser.parse("1").equals(Optional.of(new ParseResult<>('1', "")));
        assert parser.parse("12").equals(Optional.of(new ParseResult<>('1', "2")));
        assert singleDigitParser.parse("12").equals(Optional.of(new ParseResult<>(1, "2")));
    }

    static void zeroOrMoreTest() {
        Parser<List<Character>> digitsParser = digitParser().zeroOrMore();

        assert digitsParser.parse("111aaa").equals(Optional.of(new ParseResult<>(List.of('1', '1', '1'), "aaa")));
    }

    static void oneOrMoreTest() {
        Parser<List<Character>> digitsParser = digitParser().oneOrMore();

        assert digitsParser.parse("111aaa").equals(Optional.of(new ParseResult<>(List.of('1', '1', '1'), "aaa")));
        assert digitsParser.parse("1aaa").equals(Optional.of(new ParseResult<>(List.of('1'), "aaa")));
        assert digitsParser.parse("aaa").equals(Optional.empty());
    }

    static void parserTest() {
        stringParserTest();
        charParserTest();
        filterAndMapTest();
        zeroOrMoreTest();
        oneOrMoreTest();
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------PARSER-COMBINATOR-TEST-START-------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------
    static void zipTest() {
        var integerAndThenCharParser = zip(integerParser(), charParser());

        assert integerAndThenCharParser.parse("111dcd")
                .equals(Optional.of(new ParseResult<>(new Pair<>(111L, 'd'), "cd")));
    }

    static void optionalTest() {
        var parser = optional(integerParser());

        assert parser.parse("1124aa").get().equals(new ParseResult<>(Optional.of(1124L), "aa"));
        assert parser.parse("aaabbb").get().equals(new ParseResult<>(Optional.empty(), "aaabbb"));
    }

    static void combinatorTest() {
        zipTest();
        optionalTest();
    }

    // ------------------------------------------------------------------------------------------------------------------------
    // ----------------------------------------EXPRESSION-GRAMMAR-TEST-START---------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------
    static void numberTest() {
        assert ExpressionGrammar.number.parse("111").equals(
                Optional.of(new ParseResult<>(new Num.Int(111L), "")));

        assert ExpressionGrammar.number.parse("111.11a").equals(
                Optional.of(new ParseResult<>(new Num.Float(111.11D), "a")));
    }

    static void exprTest() {
        String string = "-1+((1-2)*3)+4.0/2";
        var ast = ExpressionGrammar.expr.parse(string).get().value();
        var value = eval.Eval.eval(ast);
        assert value.equals(new Num.Float(-2.0D));
    }

    static void expressionGrammarTest() {
        numberTest();
        exprTest();
    }
}

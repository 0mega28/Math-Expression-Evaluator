package parser;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Parsers {
    static <A> Parser<A> ref(Supplier<Parser<A>> parserSupplier) {
        return new Parser<>(input -> parserSupplier.get().parse(input));
    }

    static Parser<Void> consume(String string) {
        Objects.requireNonNull(string);

        return new Parser<Void>(input -> Objects.requireNonNull(input).startsWith(string)
                ? Optional.of(new ParseResult<>(null, input.substring(string.length())))
                : Optional.empty());
    }

    static Parser<Void> consumeSpace() {
        return charParser().filter(Character::isSpaceChar).zeroOrMore().map(_ -> null);
    }

    static Parser<Character> charParser() {
        return new Parser<Character>(input -> Objects.requireNonNull(input).isEmpty()
                ? Optional.empty()
                : Optional.of(new ParseResult<>(input.charAt(0), input.substring(1))));
    }

    static Parser<Character> digitParser() {
        return charParser().filter(Character::isDigit);
    }

    static Parser<Long> integerParser() {
        return digitParser().oneOrMore()
                .map(cs -> cs.stream().map(String::valueOf).collect(Collectors.joining()))
                .map(Long::parseLong);
    }

    static Parser<Double> floatParser() {
        return ParserCombinators.zip(
                ParserCombinators.first(Parsers.integerParser(), Parsers.consume(".")),
                Parsers.integerParser())
                .map(value -> Double.parseDouble(value.first() + "." + value.second()));
    }
}
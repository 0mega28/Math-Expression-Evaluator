package parser;

import java.util.Arrays;
import java.util.Optional;

public interface ParserCombinators {
    static <A, B> Parser<Pair<A, B>> zip(Parser<A> parserA, Parser<B> parserB) {
        return parserA.flatMap(valueA -> parserB.map(valueB -> new Pair<>(valueA, valueB)));
    }

    static <A> Parser<A> first(Parser<A> parserA, Parser<?> parserB) {
        return zip(parserA, parserB).map(Pair::first);
    }

    static <B> Parser<B> second(Parser<?> parserA, Parser<B> parserB) {
        return zip(parserA, parserB).map(Pair::second);
    }

    static <A> Parser<Optional<A>> optional(Parser<A> parser) {
        return new Parser<>(input -> parser.parse(input)
                .map(parseResult -> new ParseResult<>(Optional.of(parseResult.value()), parseResult.remaining()))
                .or(() -> Optional.of(new ParseResult<>(Optional.empty(), input))));
    }

    @SuppressWarnings("unchecked")
    static <A> Parser<A> oneOf(Parser<? extends A>... parsers) {
        return new Parser<A>(input -> Arrays.stream(parsers)
                .map(parser -> parser.parse(input))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(result -> (ParseResult<A>) result)
                .findFirst());
    }
}
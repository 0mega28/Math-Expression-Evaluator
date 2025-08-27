package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public record Parser<A>(ParseFunction<A> parseFunction) {

    public Optional<ParseResult<A>> parse(String input) {
        return parseFunction().parse(input);
    }

    public Parser<A> peek(Consumer<A> consumer) {
        return this.map(parseResult -> {
            consumer.accept(parseResult);
            return parseResult;
        });
    }

    public Parser<A> filter(Predicate<A> predicate) {
        return new Parser<>(input -> parse(Objects.requireNonNull(input))
                .filter(parseResult -> predicate.test(parseResult.value())));
    }

    public <B> Parser<B> map(Function<? super A, ? extends B> mapper) {
        return new Parser<>(input -> parse(Objects.requireNonNull(input))
                .map(parseResult -> new ParseResult<>(mapper.apply(parseResult.value()), parseResult.remaining())));
    }

    @SuppressWarnings("unchecked")
    public <B> Parser<B> flatMap(Function<? super A, ? extends Parser<? extends B>> flatMapper) {
        return new Parser<>(input -> parse(input)
                .flatMap(parseResult -> flatMapper.apply(parseResult.value()).parse(parseResult.remaining()))
                .map(result -> (ParseResult<B>) result));
    }

    public Parser<List<A>> zeroOrMore() {
        return new Parser<>(input -> {
            Objects.requireNonNull(input);
            List<A> result = new ArrayList<>();
            Optional<ParseResult<A>> parseResultOp;

            while ((parseResultOp = parse(input)).isPresent()) {
                result.add(parseResultOp.get().value());
                input = parseResultOp.get().remaining();
            }

            return Optional.of(new ParseResult<>(result, input));
        });
    }

    public Parser<List<A>> oneOrMore() {
        return zeroOrMore().filter(Predicate.not(List::isEmpty));
    }

}
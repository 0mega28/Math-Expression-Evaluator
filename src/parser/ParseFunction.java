package parser;

import java.util.Optional;

@FunctionalInterface
public interface ParseFunction<A> {
    Optional<ParseResult<A>> parse(String input);
}
package parser;

public record ParseResult<A>(A value, String remaining) {
}
package org.zwobble.precisely;

import java.util.function.Function;

import static org.zwobble.precisely.Indentation.indent;

class HasMatcher<T, U> implements Matcher<T> {
    private final String name;
    private final Function<T, U> extract;
    private final Matcher<U> matcher;

    HasMatcher(String name, Function<T, U> extract, Matcher<U> matcher) {
        this.name = name;
        this.extract = extract;
        this.matcher = matcher;
    }

    @Override
    public MatchResult match(T actual) {
        var value = extract.apply(actual);
        var result = matcher.match(value);
        if (result.isMatch()) {
            return MatchResult.matched();
        } else {
            return MatchResult.unmatched(String.format(
                "%s mismatched:%s",
                name,
                indent("\n" + result.explanation())
            ));
        }
    }

    @Override
    public String describe() {
        return String.format(
            "%s:%s",
            name,
            indent("\n" + matcher.describe())
        );
    }
}

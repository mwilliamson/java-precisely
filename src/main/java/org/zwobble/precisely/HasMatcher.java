package org.zwobble.precisely;

import java.util.function.Function;

class HasMatcher<T, U> implements Matcher<T> {
    private final String name;
    private final Function<T, U> extract;
    private final Matcher<? super U> matcher;

    HasMatcher(String name, Function<T, U> extract, Matcher<? super U> matcher) {
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
            return MatchResult.unmatched(TextTree.nested(
                TextTree.text(String.format("%s mismatched", name)),
                result.explanation()
            ));
        }
    }

    @Override
    public TextTree describe() {
        return TextTree.nested(
            TextTree.text(name),
            matcher.describe()
        );
    }
}

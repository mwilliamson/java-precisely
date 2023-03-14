package org.zwobble.precisely;

import java.util.List;

import static org.zwobble.precisely.Indentation.indent;

class AllOfMatcher<T> implements Matcher<T> {
    private final List<Matcher<? super T>> matchers;

    AllOfMatcher(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public MatchResult match(T actual) {
        for (var matcher : matchers) {
            var result = matcher.match(actual);
            if (!result.isMatch()) {
                return result;
            }
        }
        return MatchResult.matched();
    }

    @Override
    public String describe() {
        var description = new StringBuilder();
        description.append("all of:");

        for (var matcher : matchers) {
            description.append("\n * ");
            description.append(indent(matcher.describe(), 3));
        }

        return description.toString();
    }
}

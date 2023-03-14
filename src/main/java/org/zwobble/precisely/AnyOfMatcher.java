package org.zwobble.precisely;

import java.util.List;

import static org.zwobble.precisely.Indentation.indent;

class AnyOfMatcher<T> implements Matcher<T> {
    private final List<Matcher<? super T>> matchers;

    AnyOfMatcher(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public MatchResult match(T actual) {
        var explanation = new StringBuilder();
        explanation.append("did not match any of:");

        for (var matcher : matchers) {
            var result = matcher.match(actual);
            if (result.isMatch()) {
                return result;
            } else {
                explanation.append("\n * ");
                explanation.append(indent(matcher.describe(), 3));
                explanation.append("\n   ");
                explanation.append(indent(result.explanation()));
            }
        }

        return MatchResult.unmatched(explanation.toString());
    }

    @Override
    public String describe() {
        var description = new StringBuilder();
        description.append("any of:");

        for (var matcher : matchers) {
            description.append("\n * ");
            description.append(indent(matcher.describe(), 3));
        }

        return description.toString();
    }
}

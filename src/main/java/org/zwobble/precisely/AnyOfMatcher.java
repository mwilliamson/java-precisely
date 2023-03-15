package org.zwobble.precisely;

import java.util.ArrayList;
import java.util.List;

class AnyOfMatcher<T> implements Matcher<T> {
    private final List<Matcher<? super T>> matchers;

    AnyOfMatcher(List<Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public MatchResult match(T actual) {
        var mismatches = new ArrayList<Mismatch>();

        for (var matcher : matchers) {
            var result = matcher.match(actual);
            if (result.isMatch()) {
                return result;
            } else {
                mismatches.add(new Mismatch(matcher, result));
            }
        }

        return MatchResult.unmatched(TextTree.unorderedList(
            "did not match any of",
            mismatches.stream()
                .map(mismatch -> TextTree.nested(
                    mismatch.matcher.describe(),
                    mismatch.result.explanation()
                ))
                .toList()
        ));
    }

    @Override
    public TextTree describe() {
        return TextTree.unorderedList(
            "any of",
            matchers.stream()
                .map(Matcher::describe)
                .toList()
        );
    }

    private record Mismatch(Matcher<?> matcher, MatchResult result) {
    }
}

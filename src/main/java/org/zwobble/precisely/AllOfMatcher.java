package org.zwobble.precisely;

import java.util.List;

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
    public TextTree describe() {
        return TextTree.unorderedList(
            "all of",
            matchers.stream()
                .map(Matcher::describe)
                .toList()
        );
    }
}

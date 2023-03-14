package org.zwobble.precisely;

class NotMatcher<T> implements Matcher<T> {
    private final Matcher<T> matcher;

    NotMatcher(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    @Override
    public MatchResult match(T actual) {
        if (matcher.match(actual).isMatch()) {
            return MatchResult.unmatched("matched: " + matcher.describe());
        } else {
            return MatchResult.matched();
        }
    }

    @Override
    public String describe() {
        return "not: " + matcher.describe();
    }
}
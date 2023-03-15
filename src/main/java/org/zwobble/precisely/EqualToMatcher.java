package org.zwobble.precisely;

class EqualToMatcher<T> implements Matcher<T> {
    private final T value;

    EqualToMatcher(T value) {
        this.value = value;
    }

    @Override
    public MatchResult match(T actual) {
        if (value.equals(actual)) {
            return MatchResult.matched();
        } else {
            return MatchResult.unmatched(TextTree.object("was ", actual));
        }
    }

    @Override
    public TextTree describe() {
        return TextTree.object(value);
    }
}

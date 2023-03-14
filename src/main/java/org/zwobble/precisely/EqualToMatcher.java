package org.zwobble.precisely;

import static org.zwobble.precisely.JavaValues.valueToString;

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
            return MatchResult.unmatched("was " + valueToString(actual));
        }
    }

    @Override
    public String describe() {
        return valueToString(value);
    }
}

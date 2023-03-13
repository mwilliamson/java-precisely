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
            return MatchResult.unmatched("was " + valueToString(actual));
        }
    }

    @Override
    public String describe() {
        return valueToString(value);
    }

    private static String valueToString(Object value) {
        if (value instanceof String string) {
            return JavaStrings.toJavaLiteralString(string);
        } else {
            return value.toString();
        }
    }
}

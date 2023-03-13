package org.zwobble.precisely;

public class Matchers {
    public Matchers() {
    }

    public static <T> Matcher<T> equalTo(T value) {
        return new EqualToMatcher<>(value);
    }
}

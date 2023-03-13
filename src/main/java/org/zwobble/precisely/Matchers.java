package org.zwobble.precisely;

public class Matchers {
    private Matchers() {
    }

    public static Matcher<Object> anything() {
        return new AnythingMatcher();
    }

    public static <T> Matcher<T> equalTo(T value) {
        return new EqualToMatcher<>(value);
    }
}

package org.zwobble.precisely;

import java.util.function.Function;

public class Matchers {
    private Matchers() {
    }

    public static Matcher<Object> anything() {
        return new AnythingMatcher();
    }

    public static <T> Matcher<T> equalTo(T value) {
        return new EqualToMatcher<>(value);
    }

    public static <T, U> Matcher<T> has(String name, Function<T, U> extract, Matcher<U> matcher) {
        return new HasMatcher<>(name, extract, matcher);
    }
}

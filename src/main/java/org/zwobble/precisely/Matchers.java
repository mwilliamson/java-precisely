package org.zwobble.precisely;

import java.util.Arrays;
import java.util.function.Function;

public class Matchers {
    private Matchers() {
    }

    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<T>... matchers) {
        return new AllOfMatcher<>(Arrays.asList(matchers));
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

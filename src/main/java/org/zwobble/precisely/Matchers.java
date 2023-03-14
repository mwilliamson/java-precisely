package org.zwobble.precisely;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class Matchers {
    private Matchers() {
    }

    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<T>... matchers) {
        return new AllOfMatcher<>(Arrays.asList(matchers));
    }

    @SafeVarargs
    public static <T> Matcher<T> anyOf(Matcher<T>... matchers) {
        return new AnyOfMatcher<>(Arrays.asList(matchers));
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

    public static <T, U> Matcher<U> instanceOf(Class<T> clazz) {
        return new InstanceOfMatcher<>(clazz, Optional.empty());
    }

    @SafeVarargs
    public static <T, U> Matcher<U> instanceOf(Class<T> clazz, Matcher<T>... matchers) {
        return new InstanceOfMatcher<>(clazz, Optional.of(allOf(matchers)));
    }
}

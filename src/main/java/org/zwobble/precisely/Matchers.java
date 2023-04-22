package org.zwobble.precisely;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Matchers {
    private Matchers() {
    }

    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return new AllOfMatcher<T>(Arrays.asList(matchers));
    }

    public static <T> Matcher<T> allOf(List<Matcher<? super T>> matchers) {
        return new AllOfMatcher<T>(matchers);
    }

    @SafeVarargs
    public static <T> Matcher<T> anyOf(Matcher<? super T>... matchers) {
        return new AnyOfMatcher<T>(Arrays.asList(matchers));
    }

    public static <T> Matcher<T> anyOf(List<Matcher<? super T>> matchers) {
        return new AnyOfMatcher<T>(matchers);
    }

    public static Matcher<Object> anything() {
        return new AnythingMatcher();
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<T>> containsExactly(Matcher<? super T>... elements) {
        return new ContainsExactlyMatcher<T>(Arrays.asList(elements));
    }

    public static <T> Matcher<Iterable<T>> containsExactly(List<Matcher<? super T>> elements) {
        return new ContainsExactlyMatcher<T>(elements);
    }

    public static <T> Matcher<T> equalTo(T value) {
        return new EqualToMatcher<>(value);
    }

    public static <T, U> Matcher<T> has(String name, Function<T, U> extract, Matcher<? super U> matcher) {
        return new HasMatcher<>(name, extract, matcher);
    }

    public static <T, U> Matcher<U> instanceOf(Class<T> clazz) {
        return new InstanceOfMatcher<T, U>(clazz, List.of());
    }

    @SafeVarargs
    public static <T, U> Matcher<U> instanceOf(Class<T> clazz, Matcher<? super T>... matchers) {
        return new InstanceOfMatcher<T, U>(clazz, Arrays.asList(matchers));
    }

    public static <T, U> Matcher<U> instanceOf(Class<T> clazz, List<Matcher<? super T>> matchers) {
        return new InstanceOfMatcher<T, U>(clazz, matchers);
    }

    public static <T> Matcher<Optional<T>> isOptionalOf(Matcher<T> matcher) {
        return new IsOptionalOfMatcher<T>(matcher);
    }

    @SafeVarargs
    public static <T> Matcher<Iterable<T>> isSequence(Matcher<? super T>... elements) {
        return new IsSequenceMatcher<T>(Arrays.asList(elements));
    }

    public static <T> Matcher<Iterable<T>> isSequence(List<Matcher<? super T>> elements) {
        return new IsSequenceMatcher<T>(elements);
    }

    public static <T> Matcher<T> not(Matcher<? super T> matcher) {
        return new NotMatcher<T>(matcher);
    }
}

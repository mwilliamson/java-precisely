package org.zwobble.precisely;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Matchers {
    private Matchers() {
    }

    /// Matches a value if all sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, allOf(
    ///     has("username", User::username, equalTo("Bob")),
    ///     has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
    /// ));
    /// ```
    @SafeVarargs
    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return new AllOfMatcher<T>(Arrays.asList(matchers));
    }

    /// Matches a value if all sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, allOf(List.of(
    ///     has("username", User::username, equalTo("Bob")),
    ///     has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
    /// )));
    /// ```
    public static <T> Matcher<T> allOf(List<Matcher<? super T>> matchers) {
        return new AllOfMatcher<T>(matchers);
    }

    /// Matches a value if any sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, anyOf(
    ///     equalTo("Alice"),
    ///     equalTo("Bob")
    /// ));
    /// ```
    @SafeVarargs
    public static <T> Matcher<T> anyOf(Matcher<? super T>... matchers) {
        return new AnyOfMatcher<T>(Arrays.asList(matchers));
    }

    /// Matches a value if any sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, anyOf(List.of(
    ///     equalTo("Alice"),
    ///     equalTo("Bob")
    /// )));
    /// ```
    public static <T> Matcher<T> anyOf(List<Matcher<? super T>> matchers) {
        return new AnyOfMatcher<T>(matchers);
    }

    /// Matches all values.
    public static Matcher<Object> anything() {
        return new AnythingMatcher();
    }

    /// Matches an iterable if it has the same elements in any order. For instance:
    ///
    /// ```
    /// assertThat(result, containsExactly(equalTo("a"), equalTo("b")));
    /// // Matches List.of("a", "b") and List.of("b", "a"),
    /// // but not List.of("a", "a", "b") nor List.of("a") nor List.of("a", "b", "c")
    /// ```
    @SafeVarargs
    public static <T> Matcher<Iterable<T>> containsExactly(Matcher<? super T>... elements) {
        return new ContainsExactlyMatcher<T>(Arrays.asList(elements));
    }

    /// Matches an iterable if it has the same elements in any order. For instance:
    ///
    /// ```
    /// assertThat(result, containsExactly(List.of(equalTo("a"), equalTo("b"))));
    /// // Matches List.of("a", "b") and List.of("b", "a"),
    /// // but not List.of("a", "a", "b") nor List.of("a") nor List.of("a", "b", "c")
    /// ```
    public static <T> Matcher<Iterable<T>> containsExactly(List<Matcher<? super T>> elements) {
        return new ContainsExactlyMatcher<T>(elements);
    }

    /// Matches a value if it is equal to `value` using `.equals()`. For instance:
    ///
    /// ```
    /// assertThat(result, equalTo("hello"));
    /// ```
    public static <T> Matcher<T> equalTo(T value) {
        return new EqualToMatcher<>(value);
    }

    /// Matches a value if an extracted subvalue matches the given matcher. For instance:
    ///
    /// ```
    /// assertThat(result, has("username", User::username, equalTo("Bob")));
    /// ```
    public static <T, U> Matcher<T> has(String name, Function<T, U> extract, Matcher<? super U> matcher) {
        return new HasMatcher<>(name, extract, matcher);
    }

    /// Matches a value if it is an instance of the given class. For instance:
    ///
    /// ```
    /// assertThat(result, instanceOf(User.class));
    /// ```
    public static <T, U> Matcher<U> instanceOf(Class<T> clazz) {
        return new InstanceOfMatcher<T, U>(clazz, List.of());
    }

    /// Matches a value if it is an instance of the given class, and if all sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, instanceOf(
    ///     User.class,
    ///     has("username", User::username, equalTo("Bob"))
    /// ));
    /// ```
    @SafeVarargs
    public static <T, U> Matcher<U> instanceOf(Class<T> clazz, Matcher<? super T>... matchers) {
        return new InstanceOfMatcher<T, U>(clazz, Arrays.asList(matchers));
    }

    /// Matches a value if it is an instance of the given class, and if all sub-matchers match. For instance:
    ///
    /// ```
    /// assertThat(result, instanceOf(
    ///     User.class,
    ///     List.of(has("username", User::username, equalTo("Bob")))
    /// ));
    /// ```
    public static <T, U> Matcher<U> instanceOf(Class<T> clazz, List<Matcher<? super T>> matchers) {
        return new InstanceOfMatcher<T, U>(clazz, matchers);
    }

    /// Matches `Optional.empty()`.
    public static <T> Matcher<Optional<? extends T>> isOptionalEmpty() {
        return new IsOptionalEmptyMatcher<>();
    }

    /// Matches an optional value if it contains a value matching the given matcher. For instance:
    ///
    /// ```
    /// assertThat(result, isOptionalOf(equalTo("Bob")));
    /// ```
    public static <T> Matcher<Optional<? extends T>> isOptionalOf(Matcher<? super T> matcher) {
        return new IsOptionalOfMatcher<T>(matcher);
    }

    /// Matches an iterable if it has the same elements in the same order. For instance:
    ///
    /// ```
    /// assertThat(result, isSequence(equalTo("a"), equalTo("b")));
    /// // Matches List.of("a", "b")
    /// // but not List.of("b", "a") nor List.of("a", "b", "c") nor List.of("c", "a", "b")
    /// ```
    @SafeVarargs
    public static <T> Matcher<Iterable<? extends T>> isSequence(Matcher<? super T>... elements) {
        return new IsSequenceMatcher<T>(Arrays.asList(elements));
    }

    /// Matches an iterable if it has the same elements in the same order. For instance:
    ///
    /// ```
    /// assertThat(result, isSequence(List.of(equalTo("a"), equalTo("b"))));
    /// // Matches List.of("a", "b")
    /// // but not List.of("b", "a") nor List.of("a", "b", "c") nor List.of("c", "a", "b")
    /// ```
    public static <T> Matcher<Iterable<? extends T>> isSequence(List<Matcher<? super T>> elements) {
        return new IsSequenceMatcher<T>(elements);
    }

    /// Negates a matcher. For instance:
    ///
    /// ```
    /// assertThat(result, not(equalTo("hello")));
    /// ```
    public static <T> Matcher<T> not(Matcher<? super T> matcher) {
        return new NotMatcher<T>(matcher);
    }
}

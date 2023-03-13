package org.zwobble.precisely;

public class AssertThat {
    public AssertThat() {
    }

    public static <T> void assertThat(T value, Matcher<T> matcher) {
        var result = matcher.match(value);
        if (!result.isMatch()) {
            throw new AssertionError(String.format("Expected:\n  %s\nbut:\n  %s", matcher.describe(), result.explanation()));
        }
    }
}

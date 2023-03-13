package org.zwobble.precisely;

import static org.zwobble.precisely.Indentation.indent;

public class AssertThat {
    private AssertThat() {
    }

    public static <T> void assertThat(T value, Matcher<T> matcher) {
        var result = matcher.match(value);
        if (!result.isMatch()) {
            throw new AssertionError(String.format(
                "\nExpected:%s\nbut:%s",
                indent("\n" + matcher.describe()),
                indent("\n" + result.explanation())
            ));
        }
    }
}

package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaStringsTests {
    @Test
    public void stringIsSurroundedByDoubleQuotes() {
        var result = JavaStrings.toJavaLiteralString("hello");

        assertEquals("\"hello\"", result);
    }

    @Test
    public void specialCharactersAreEscaped() {
        var result = JavaStrings.toJavaLiteralString("\\\b\t\n\f\r\"");

        assertEquals("\"\\\\\\b\\t\\n\\f\\r\\\"\"", result);
    }
}

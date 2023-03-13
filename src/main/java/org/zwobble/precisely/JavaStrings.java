package org.zwobble.precisely;

class JavaStrings {
    JavaStrings() {
    }

    static String toJavaLiteralString(String value) {
        var escapedValue = value
            .replace("\\", "\\\\")
            .replace("\b", "\\b")
            .replace("\t", "\\t")
            .replace("\n", "\\n")
            .replace("\f", "\\f")
            .replace("\r", "\\r")
            .replace("\"", "\\\"");
        return String.format("\"%s\"", escapedValue);
    }
}

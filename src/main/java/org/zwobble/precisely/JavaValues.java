package org.zwobble.precisely;

class JavaValues {
    JavaValues() {
    }

    static String valueToString(Object value) {
        if (value instanceof String string) {
            return JavaStrings.toJavaLiteralString(string);
        } else {
            return value.toString();
        }
    }
}

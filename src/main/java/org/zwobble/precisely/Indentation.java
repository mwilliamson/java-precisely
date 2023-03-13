package org.zwobble.precisely;

class Indentation {
    Indentation() {
    }

    static String indent(String text) {
        return text.replace("\n", "\n  ");
    }

    static String indent(String text, int width) {
        return text.replace("\n", "\n" + " ".repeat(width));
    }
}

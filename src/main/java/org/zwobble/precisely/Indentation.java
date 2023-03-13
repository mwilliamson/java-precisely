package org.zwobble.precisely;

class Indentation {
    public Indentation() {
    }

    static String indent(String text) {
        return text.replace("\n", "\n  ");
    }
}

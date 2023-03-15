package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTreeTests {
    @Test
    public void writtenStringsAreIndentedAccordingToCurrentIndentLevel() {
        var writer = new TextTreeWriter();
        writer.indented(3, () -> {
            writer.write("hello\nworld");
        });

        var result = writer.toString();

        assertEquals("hello\n   world", result);
    }
}

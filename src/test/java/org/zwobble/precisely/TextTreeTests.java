package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTreeTests {
    @Test
    public void linesAreSeparatedByNewLine() {
        var tree = TextTree.lines(
            TextTree.text("hello"),
            TextTree.text("world")
        );

        var result = tree.toString();

        assertEquals("hello\nworld", result);
    }

    @Test
    public void nestedIndentsSecondLineFromFirstLine() {
        var tree = TextTree.nested(
            TextTree.text("hello"),
            TextTree.text("world")
        );

        var result = tree.toString();

        assertEquals("hello:\n  world", result);
    }

    @Test
    public void objectConvertStringToJavaLiteralString() {
        var tree = TextTree.object("hello");

        var result = tree.toString();

        assertEquals("\"hello\"", result);
    }

    @Test
    public void objectConvertNonStringToStringUsingToString() {
        var tree = TextTree.object(42);

        var result = tree.toString();

        assertEquals("42", result);
    }

    @Test
    public void orderedListCreatesNumberedListWithIndentation() {
        var tree = TextTree.orderedList(
            "fruit",
            List.of(
                TextTree.text("apples\n1"),
                TextTree.text("bananas\n2")
            )
        );

        var result = tree.toString();

        assertEquals("""
            fruit:
             0: apples
                1
             1: bananas
                2""", result);
    }

    @Test
    public void textIsWrittenVerbatim() {
        var tree = TextTree.text("apples");

        var result = tree.toString();

        assertEquals("apples", result);
    }

    @Test
    public void unorderedListCreatesBulletedListWithIndentation() {
        var tree = TextTree.unorderedList(
            "fruit",
            List.of(
                TextTree.text("apples\n1"),
                TextTree.text("bananas\n2")
            )
        );

        var result = tree.toString();

        assertEquals("""
            fruit:
             * apples
               1
             * bananas
               2""", result);
    }

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

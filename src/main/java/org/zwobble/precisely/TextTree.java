package org.zwobble.precisely;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public abstract sealed class TextTree {
    public static TextTree empty() {
        return text("");
    }

    public static TextTree lines(TextTree... lines) {
        return new TextTreeLines(Arrays.asList(lines));
    }

    public static TextTree nested(TextTree outer, TextTree inner) {
        return new TextTreeNested(outer, inner);
    }

    public static TextTree object(Object value) {
        return new TextTreeObject("", value);
    }

    public static TextTree object(String prefix, Object value) {
        return new TextTreeObject(prefix, value);
    }

    public static TextTree orderedList(String heading, List<TextTree> children) {
        return new TextTreeList(index -> index + ":", heading, children);
    }

    public static TextTree text(String text) {
        return new TextTreeText(text);
    }

    public static TextTree unorderedList(String heading, List<TextTree> children) {
        return new TextTreeList(index -> "*", heading, children);
    }

    public String toString() {
        var writer = new TextTreeWriter();
        write(writer);
        return writer.toString();
    }

    abstract void write(TextTreeWriter writer);
}

class TextTreeWriter {
    private final StringBuilder builder = new StringBuilder();
    private int indentation = 0;

    public void write(String text) {
        builder.append(text);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public void indented(int indentatation, Action action) {
        this.indentation += indentatation;
        try {
            action.run();
        } finally {
            this.indentation -= indentatation;
        }
    }

    public void newLine() {
        builder.append("\n");
        builder.append(" ".repeat(indentation));
    }
}

interface Action {
    void run();
}

final class TextTreeLines extends TextTree {
    private final List<TextTree> lines;

    public TextTreeLines(List<TextTree> lines) {
        this.lines = lines;
    }

    @Override
    void write(TextTreeWriter writer) {
        boolean isFirst = true;
        for (var line : lines) {
            if (!isFirst) {
                writer.write("\n");
            }
            line.write(writer);
            isFirst = false;
        }
    }
}

final class TextTreeList extends TextTree {
    private final Function<Integer, String> bullet;
    private final String heading;
    private final List<TextTree> children;

    public TextTreeList(
        Function<Integer, String> bullet,
        String heading,
        List<TextTree> children
    ) {
        this.bullet = bullet;
        this.heading = heading;
        this.children = children;
    }

    @Override
    void write(TextTreeWriter writer) {
        writer.write(heading);
        writer.write(":");
        var childIndex = 0;
        for (var child : children) {
            writer.newLine();
            var prefix = " " + bullet.apply(childIndex) + " ";
            writer.write(prefix);
            writer.indented(prefix.length(), () -> {
                child.write(writer);
            });
            childIndex += 1;
        }
    }
}

final class TextTreeNested extends TextTree {
    private final TextTree outer;
    private final TextTree inner;

    public TextTreeNested(TextTree outer, TextTree inner) {
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    void write(TextTreeWriter writer) {
        outer.write(writer);
        writer.write(":");
        writer.indented(2, () -> {
            writer.newLine();
            inner.write(writer);
        });
    }
}

final class TextTreeObject extends TextTree {
    private final String prefix;
    private final Object value;

    TextTreeObject(String prefix, Object value) {
        this.prefix = prefix;
        this.value = value;
    }

    @Override
    void write(TextTreeWriter writer) {
        writer.write(prefix);
        if (value instanceof String string) {
            writer.write(JavaStrings.toJavaLiteralString(string));
        } else {
            writer.write(value.toString());
        }
    }
}

final class TextTreeText extends TextTree {
    private final String text;

    public TextTreeText(String text) {
        this.text = text;
    }

    @Override
    void write(TextTreeWriter writer) {
        writer.write(text);
    }
}

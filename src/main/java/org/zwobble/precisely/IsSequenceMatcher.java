package org.zwobble.precisely;

import java.util.List;

import static org.zwobble.precisely.Indentation.indent;
import static org.zwobble.precisely.JavaValues.valueToString;

public class IsSequenceMatcher<T> implements Matcher<Iterable<T>> {
    private final List<Matcher<? super T>> elementMatchers;

    IsSequenceMatcher(List<Matcher<? super T>> elementMatchers) {
        this.elementMatchers = elementMatchers;
    }

    @Override
    public MatchResult match(Iterable<T> actual) {
        var actualIterator = actual.iterator();
        var elementMatchersIterator = elementMatchers.iterator();
        var elementIndex = 0;

        while (actualIterator.hasNext()) {
            if (!elementMatchersIterator.hasNext()) {
                var explanation = new StringBuilder();
                explanation.append("had extra elements:");
                while (actualIterator.hasNext()) {
                    var actualElement = actualIterator.next();
                    explanation.append("\n * ");
                    explanation.append(indent(valueToString(actualElement), 3));
                }
                return MatchResult.unmatched(explanation.toString());
            }

            var actualElement = actualIterator.next();
            var elementMatcher = elementMatchersIterator.next();

            var elementResult = elementMatcher.match(actualElement);
            if (!elementResult.isMatch()) {
                return MatchResult.unmatched(String.format(
                    "element at index %s mismatched:%s",
                    elementIndex,
                    indent("\n" + elementResult.explanation(), 2)
                ));
            }
            elementIndex++;
        }

        if (elementMatchersIterator.hasNext()) {
            if (elementIndex == 0) {
                return MatchResult.unmatched("iterable was empty");
            } else {
                return MatchResult.unmatched(String.format(
                    "element at index %s was missing",
                    elementIndex
                ));
            }
        }

        return MatchResult.matched();
    }

    @Override
    public String describe() {
        if (elementMatchers.isEmpty()) {
            return "empty iterable";
        } else {
            var description = new StringBuilder();
            description.append("iterable containing in order:");

            var elementIndex = 0;
            for (var elementMatcher : elementMatchers) {
                var prefix = String.format(" %s: ", elementIndex);
                description.append("\n");
                description.append(prefix);
                description.append(indent(elementMatcher.describe(), prefix.length()));
                elementIndex++;
            }

            return description.toString();
        }
    }
}

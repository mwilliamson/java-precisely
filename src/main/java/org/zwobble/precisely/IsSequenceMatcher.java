package org.zwobble.precisely;

import java.util.ArrayList;
import java.util.List;

import static org.zwobble.precisely.JavaValues.valueToString;

class IsSequenceMatcher<T> implements Matcher<Iterable<T>> {
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
                var extraElements = new ArrayList<TextTree>();
                while (actualIterator.hasNext()) {
                    var actualElement = actualIterator.next();
                    extraElements.add(TextTree.text(valueToString(actualElement)));
                }
                return MatchResult.unmatched(TextTree.unorderedList(
                    "had extra elements",
                    extraElements
                ));
            }

            var actualElement = actualIterator.next();
            var elementMatcher = elementMatchersIterator.next();

            var elementResult = elementMatcher.match(actualElement);
            if (!elementResult.isMatch()) {
                return MatchResult.unmatched(TextTree.nested(
                    TextTree.text(String.format(
                        "element at index %s mismatched",
                        elementIndex
                    )),
                    elementResult.explanation()
                ));
            }
            elementIndex++;
        }

        if (elementMatchersIterator.hasNext()) {
            if (elementIndex == 0) {
                return MatchResult.unmatched(TextTree.text("iterable was empty"));
            } else {
                return MatchResult.unmatched(TextTree.text(String.format(
                    "element at index %s was missing",
                    elementIndex
                )));
            }
        }

        return MatchResult.matched();
    }

    @Override
    public TextTree describe() {
        if (elementMatchers.isEmpty()) {
            return TextTree.text("empty iterable");
        } else {
            return TextTree.orderedList(
                "iterable containing in order",
                elementMatchers.stream()
                    .map(Matcher::describe)
                    .toList()
            );
        }
    }
}

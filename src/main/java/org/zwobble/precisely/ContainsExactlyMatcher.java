package org.zwobble.precisely;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ContainsExactlyMatcher<T> implements Matcher<Iterable<T>> {
    private final List<Matcher<? super T>> elementMatchers;

    ContainsExactlyMatcher(List<Matcher<? super T>> elementMatchers) {
        this.elementMatchers = elementMatchers;
    }

    @Override
    public MatchResult match(Iterable<T> actual) {
        var actualElements = new ArrayList<T>();
        for (var actualElement : actual) {
            actualElements.add(actualElement);
        }

        if (actualElements.isEmpty() && !elementMatchers.isEmpty()) {
            return MatchResult.unmatched(TextTree.text("iterable was empty"));
        }

        var matchedElementIndexes = new HashSet<Integer>();

        for (var elementMatcher : elementMatchers) {
            var elementResult = matchElement(elementMatcher, actualElements, matchedElementIndexes);
            if (!elementResult.isMatch()) {
                return elementResult;
            }
        }

        if (matchedElementIndexes.size() == actualElements.size()) {
            return MatchResult.matched();
        } else {
            var extraElements = new ArrayList<TextTree>();
            for (var elementIndex = 0; elementIndex < actualElements.size(); elementIndex++) {
                if (!matchedElementIndexes.contains(elementIndex)) {
                    var element = actualElements.get(elementIndex);
                    extraElements.add(TextTree.object(element));
                }
            }
            return MatchResult.unmatched(TextTree.unorderedList(
                "had extra elements",
                extraElements
            ));
        }
    }

    private MatchResult matchElement(
        Matcher<? super T> elementMatcher,
        List<T> actualElements,
        Set<Integer> matchedElementIndexes
    ) {
        var mismatches = new ArrayList<TextTree>();

        for (var elementIndex = 0; elementIndex < actualElements.size(); elementIndex++) {
            var actualElement = actualElements.get(elementIndex);
            if (matchedElementIndexes.contains(elementIndex)) {
                mismatches.add(TextTree.nested(
                    TextTree.object(actualElement),
                    TextTree.text("already matched")
                ));
            } else {
                var elementResult = elementMatcher.match(actualElement);
                if (elementResult.isMatch()) {
                    matchedElementIndexes.add(elementIndex);
                    return elementResult;
                } else {
                    mismatches.add(TextTree.nested(
                        TextTree.object(actualElement),
                        elementResult.explanation()
                    ));
                }
            }
        }

        return MatchResult.unmatched(TextTree.lines(
            TextTree.nested(
                TextTree.text("was missing element"),
                elementMatcher.describe()
            ),
            TextTree.unorderedList(
                "These elements were in the iterable, but did not match the missing element",
                mismatches
            )
        ));
    }

    @Override
    public TextTree describe() {
        if (elementMatchers.isEmpty()) {
            return TextTree.text("empty iterable");
        } else if (elementMatchers.size() == 1) {
            return TextTree.unorderedList(
                "iterable containing 1 element",
                List.of(elementMatchers.get(0).describe())
            );
        } else {
            return TextTree.unorderedList(
                String.format("iterable containing these %s elements in any order", elementMatchers.size()),
                elementMatchers.stream()
                    .map(Matcher::describe)
                    .toList()
            );
        }
    }
}

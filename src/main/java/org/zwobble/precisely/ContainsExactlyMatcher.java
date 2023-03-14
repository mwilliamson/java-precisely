package org.zwobble.precisely;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.zwobble.precisely.Indentation.indent;
import static org.zwobble.precisely.JavaValues.valueToString;

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
            return MatchResult.unmatched("iterable was empty");
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
            var explanation = new StringBuilder();
            explanation.append("had extra elements:");
            for (var elementIndex = 0; elementIndex < actualElements.size(); elementIndex++) {
                if (!matchedElementIndexes.contains(elementIndex)) {
                    var element = actualElements.get(elementIndex);
                    explanation.append("\n * ");
                    explanation.append(indent(valueToString(element), 3));
                }
            }
            return MatchResult.unmatched(explanation.toString());
        }
    }

    private MatchResult matchElement(
        Matcher<? super T> elementMatcher,
        List<T> actualElements,
        Set<Integer> matchedElementIndexes
    ) {
        var mismatches = new ArrayList<MatchResult>();

        for (var elementIndex = 0; elementIndex < actualElements.size(); elementIndex++) {
            if (matchedElementIndexes.contains(elementIndex)) {
                mismatches.add(MatchResult.unmatched("already matched"));
            } else {
                var actualElement = actualElements.get(elementIndex);
                var elementResult = elementMatcher.match(actualElement);
                if (elementResult.isMatch()) {
                    matchedElementIndexes.add(elementIndex);
                    return elementResult;
                } else {
                    mismatches.add(elementResult);
                }
            }
        }

        var explanation = new StringBuilder();
        explanation.append("was missing element:");
        explanation.append(indent("\n" + elementMatcher.describe()));
        explanation.append("\nThese elements were in the iterable, but did not match the missing element:");
        for (var elementIndex = 0; elementIndex < actualElements.size(); elementIndex++) {
            var actualElement = actualElements.get(elementIndex);
            var mismatch = mismatches.get(elementIndex);
            explanation.append("\n * ");
            explanation.append(indent(valueToString(actualElement), 3));
            explanation.append(": ");
            explanation.append(indent(mismatch.explanation(), 3));
        }
        return MatchResult.unmatched(explanation.toString());
    }

    @Override
    public String describe() {
        if (elementMatchers.isEmpty()) {
            return "empty iterable";
        } else if (elementMatchers.size() == 1) {
            return "iterable containing 1 element:\n * " + indent(elementMatchers.get(0).describe(), 3);
        } else {
            var explanation = new StringBuilder();
            explanation.append(String.format("iterable containing these %s elements in any order:", elementMatchers.size()));
            for (var elementMatcher : elementMatchers) {
                explanation.append("\n * ");
                explanation.append(indent(elementMatcher.describe(), 3));
            }
            return explanation.toString();
        }
    }
}

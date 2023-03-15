package org.zwobble.precisely;

import java.util.List;

class InstanceOfMatcher<T, U> implements Matcher<U> {
    private final Class<T> clazz;
    private final List<Matcher<? super T>> matchers;

    InstanceOfMatcher(Class<T> clazz, List<Matcher<? super T>> matchers) {
        this.clazz = clazz;
        this.matchers = matchers;
    }

    @Override
    public MatchResult match(U actual) {
        if (clazz.isInstance(actual)) {
            return new AllOfMatcher<T>(matchers).match((T) actual);
        } else {
            return MatchResult.unmatched(TextTree.text("was instance of " + actual.getClass().getTypeName()));
        }

    }

    @Override
    public TextTree describe() {
        var description = "is instance of " + clazz.getTypeName();
        if (matchers.isEmpty()) {
            return TextTree.text(description);
        } else {
            return TextTree.unorderedList(
                description + " and all of",
                matchers.stream()
                    .map(Matcher::describe)
                    .toList()
            );
        }
    }
}

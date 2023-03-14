package org.zwobble.precisely;

import java.util.Optional;

class InstanceOfMatcher<T, U> implements Matcher<U> {
    private final Class<T> clazz;
    private final Optional<Matcher<T>> matcher;

    InstanceOfMatcher(Class<T> clazz, Optional<Matcher<T>> matcher) {
        this.clazz = clazz;
        this.matcher = matcher;
    }

    @Override
    public MatchResult match(U actual) {
        if (clazz.isInstance(actual)) {
            if (matcher.isPresent()) {
                return matcher.get().match((T) actual);
            } else {
                return MatchResult.matched();
            }

        } else {
            return MatchResult.unmatched("was instance of " + actual.getClass().getTypeName());
        }

    }

    @Override
    public String describe() {
        var description = "is instance of " + clazz.getTypeName();
        if (matcher.isPresent()) {
            return description + " and " + matcher.get().describe();
        } else {
            return description;
        }
    }
}

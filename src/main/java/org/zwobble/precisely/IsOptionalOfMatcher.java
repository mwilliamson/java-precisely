package org.zwobble.precisely;

import java.util.Optional;

public class IsOptionalOfMatcher<T> implements Matcher<Optional<T>> {
    private final Matcher<? super T> valueMatcher;

    public IsOptionalOfMatcher(Matcher<? super T> valueMatcher) {
        this.valueMatcher = valueMatcher;
    }

    @Override
    public MatchResult match(Optional<T> actual) {
        if (actual.isEmpty()) {
            return MatchResult.unmatched(TextTree.text("was empty"));
        } else {
            var result = valueMatcher.match(actual.get());
            if (result.isMatch()) {
                return MatchResult.matched();
            } else {
                return MatchResult.unmatched(TextTree.nested(
                    TextTree.text("optional value"),
                    result.explanation()
                ));
            }
        }
    }

    @Override
    public TextTree describe() {
        return TextTree.nested(TextTree.text("optional value"), valueMatcher.describe());
    }
}

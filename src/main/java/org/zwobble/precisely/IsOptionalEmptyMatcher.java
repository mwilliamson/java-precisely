package org.zwobble.precisely;

import java.util.Optional;

public class IsOptionalEmptyMatcher<T> implements Matcher<Optional<? extends T>> {
    @Override
    public MatchResult match(Optional<? extends T> actual) {
        if (actual.isEmpty()) {
            return MatchResult.matched();
        } else {
            return MatchResult.unmatched(TextTree.nested(
                TextTree.text("optional had value"),
                TextTree.object(actual.get())
            ));
        }
    }

    @Override
    public TextTree describe() {
        return TextTree.text("empty optional");
    }
}

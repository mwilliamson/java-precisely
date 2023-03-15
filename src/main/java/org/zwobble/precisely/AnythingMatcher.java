package org.zwobble.precisely;

class AnythingMatcher implements Matcher<Object> {
    @Override
    public MatchResult match(Object actual) {
        return MatchResult.matched();
    }

    @Override
    public TextTree describe() {
        return TextTree.text("anything");
    }
}

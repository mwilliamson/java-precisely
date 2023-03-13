package org.zwobble.precisely;

public class AnythingMatcher implements Matcher<Object> {
    @Override
    public MatchResult match(Object actual) {
        return MatchResult.matched();
    }

    @Override
    public String describe() {
        return "anything";
    }
}

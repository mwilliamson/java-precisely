package org.zwobble.precisely;

public record MatchResult(boolean isMatch, String explanation) {
    public static MatchResult matched() {
        return new MatchResult(true, "");
    }

    public static MatchResult unmatched(String explanation) {
        return new MatchResult(false, explanation);
    }
}

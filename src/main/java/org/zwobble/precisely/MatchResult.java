package org.zwobble.precisely;

public class MatchResult {
    private static MatchResult MATCHED = new MatchResult(true, TextTree.empty());

    public static MatchResult matched() {
        return MATCHED;
    }

    public static MatchResult unmatched(TextTree explanation) {
        return new MatchResult(false, explanation);
    }

    private final boolean isMatch;
    private final TextTree explanation;

    private MatchResult(boolean isMatch, TextTree explanation) {
        this.isMatch = isMatch;
        this.explanation = explanation;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public TextTree explanation() {
        return explanation;
    }
}

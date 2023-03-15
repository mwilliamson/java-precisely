package org.zwobble.precisely;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Assertions {
    private Assertions() {
    }

    static void assertUnmatched(String expectedExplanation, MatchResult result) {
        assertFalse(result.isMatch());
        assertEquals(expectedExplanation, result.explanation().toString());
    }
}

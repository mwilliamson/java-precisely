package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.equalTo;
import static org.zwobble.precisely.Matchers.not;

public class NotTests {
    @Test
    public void matchesWhenNegatedMatcherDoesNotMatch() {
        var matcher = not(equalTo(1));

        var result = matcher.match(2);

        assertEquals(matched(), result);
    }

    @Test
    public void doesNotMatchWhenNegatedMatcherMatches() {
        var matcher = not(equalTo(1));

        var result = matcher.match(1);

        assertUnmatched("matched:\n  1", result);
    }

    @Test
    public void descriptionIncludesDescriptionOfNegatedMatcher() {
        var matcher = not(equalTo(1));

        var result = matcher.describe();

        assertEquals("not:\n  1", result.toString());
    }
}

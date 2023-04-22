package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.equalTo;
import static org.zwobble.precisely.Matchers.isOptionalOf;

public class IsOptionalOfTests {
    @Test
    public void matchesWhenValueIsSomeAndSomeValueMatches() {
        var matcher = isOptionalOf(equalTo(1));

        var result = matcher.match(Optional.of(1));

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenOptionalIsEmpty() {
        var matcher = isOptionalOf(equalTo(1));

        var result = matcher.match(Optional.empty());

        assertUnmatched("was empty", result);
    }

    @Test
    public void mismatchesWhenValueInOptionalDoesNotMatch() {
        var matcher = isOptionalOf(equalTo(1));

        var result = matcher.match(Optional.of(2));

        assertUnmatched("optional value:\n  was 2", result);
    }

    @Test
    public void descriptionContainsDescriptionOfValueMatcher() {
        var matcher = isOptionalOf(equalTo(1));

        var result = matcher.describe();

        assertEquals("optional value:\n  1", matcher.describe().toString());
    }
}

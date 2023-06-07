package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.*;

public class IsOptionalEmptyTests {
    @Test
    public void matchesWhenActualIsNone() {
        var matcher = isOptionalEmpty();

        var result = matcher.match(Optional.empty());

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenOptionalIsNotEmpty() {
        var matcher = isOptionalEmpty();

        var result = matcher.match(Optional.of(1));

        assertUnmatched("optional had value:\n  1", result);
    }

    @Test
    public void descriptionIsEmptyOptional() {
        var matcher = isOptionalEmpty();

        var result = matcher.describe();

        assertEquals("empty optional", result.toString());
    }
}

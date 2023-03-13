package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.MatchResult.unmatched;
import static org.zwobble.precisely.Matchers.equalTo;

public class EqualToTests {
    @Test
    public void matchesWhenValuesAreEqual() {
        assertEquals(matched(), equalTo(1).match(1));
    }

    @Test
    public void explanationOfMismatchContainsToStringOfActual() {
        assertEquals(unmatched("was 2"), equalTo(1).match(2));
    }

    @Test
    public void descriptionIsToStringOfValue() {
        assertEquals("1", equalTo(1).describe());
    }
}

package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.equalTo;

public class EqualToTests {
    @Test
    public void matchesWhenValuesAreEqual() {
        assertEquals(matched(), equalTo(1).match(1));
    }

    @Test
    public void explanationOfMismatchContainsToStringOfActual() {
        assertUnmatched("was 2", equalTo(1).match(2));
    }

    @Test
    public void explanationOfStringMismatchIsWrittenAsJavaString() {
        assertUnmatched("was \"Hello\"", equalTo("hello").match("Hello"));
    }

    @Test
    public void descriptionIsToStringOfValue() {
        assertEquals("1", equalTo(1).describe().toString());
    }

    @Test
    public void descriptionOfStringIsWrittenAsJavaString() {
        assertEquals("\"hello\"", equalTo("hello").describe().toString());
    }
}

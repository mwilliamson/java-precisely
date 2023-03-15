package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.anything;

public class AnythingTests {
    @Test
    public void matchesAnything() {
        assertEquals(matched(), anything().match(4));
        assertEquals(matched(), anything().match("Hello"));
    }

    @Test
    public void descriptionIsAnything() {
        assertEquals("anything", anything().describe().toString());
    }
}

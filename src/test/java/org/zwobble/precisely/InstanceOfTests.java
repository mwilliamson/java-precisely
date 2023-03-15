package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.*;

public class InstanceOfTests {
    @Test
    public void givenNoSubmatchersThenMatchesWhenValueIsInstanceOfClass() {
        var matcher = instanceOf(String.class);

        var result = matcher.match("hello");

        assertEquals(matched(), result);
    }

    @Test
    public void givenNoSubmatchersThenExplanationOfMismatchContainsActualType() {
        var matcher = instanceOf(String.class);

        var result = matcher.match(42);

        assertUnmatched("was instance of java.lang.Integer", result);
    }

    @Test
    public void givenSubmatchersThenMatchesWhenValueIsInstanceOfClassAndSubmatchersMatch() {
        var matcher = instanceOf(String.class, has("length", String::length, equalTo(5)));

        var result = matcher.match("hello");

        assertEquals(matched(), result);
    }

    @Test
    public void givenSubmatchersWhenSubmatcherMismatchesThenExplanationContainsSubmatcherMismatchExplanation() {
        var matcher = instanceOf(String.class, has("length", String::length, equalTo(5)));

        var result = matcher.match("helloo");

        assertUnmatched("length mismatched:\n  was 6", result);
    }

    @Test
    public void whenThereAreNoSubmatchersThenDescriptionIncludesExpectedType() {
        var matcher = instanceOf(String.class);

        var result = matcher.describe();

        assertEquals("is instance of java.lang.String", result.toString());
    }

    @Test
    public void whenThereAreSubmatchersThenDescriptionIncludesExpectedTypeAndSubmatchers() {
        var matcher = instanceOf(String.class, has("length", String::length, equalTo(5)));

        var result = matcher.describe();

        assertEquals("is instance of java.lang.String and all of:\n * length:\n     5", result.toString());
    }
}

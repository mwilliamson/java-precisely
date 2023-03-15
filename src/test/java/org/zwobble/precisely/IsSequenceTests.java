package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.equalTo;
import static org.zwobble.precisely.Matchers.isSequence;

public class IsSequenceTests {
    @Test
    public void matchesWhenAllSubmatchersMatchOneItemWithNoItemsLeftover() {
        var matcher = isSequence(equalTo("apple"), equalTo("banana"));

        var result = matcher.match(List.of("apple", "banana"));

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenItemsAreInWrongOrder() {
        var matcher = isSequence(equalTo("apple"), equalTo("banana"));

        var result = matcher.match(List.of("banana", "apple"));

        assertUnmatched("element at index 0 mismatched:\n  was \"banana\"", result);
    }

    @Test
    public void mismatchesWhenItemIsMissing() {
        var matcher = isSequence(equalTo("apple"), equalTo("banana"), equalTo("coconut"));

        var result = matcher.match(List.of("apple", "banana"));

        assertUnmatched("element at index 2 was missing", result);
    }

    @Test
    public void mismatchesWhenItemIsExpectedButIterableIsEmpty() {
        var matcher = isSequence(equalTo("apple"));

        var result = matcher.match(List.of());

        assertUnmatched("iterable was empty", result);
    }

    @Test
    public void whenEmptyIterableIsExpectedThenEmptyIterableMatches() {
        var matcher = isSequence();

        var result = matcher.match(List.of());

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenContainsExtraElements() {
        var matcher = isSequence(equalTo("apple"));

        var result = matcher.match(List.of("apple", "banana", "coconut"));

        assertUnmatched("had extra elements:\n * \"banana\"\n * \"coconut\"", result);
    }

    @Test
    public void whenThereAreZeroSubmatchersThenDescriptionIsOfEmptyIterable() {
        var matcher = isSequence();

        var result = matcher.describe();

        assertEquals("empty iterable", result.toString());
    }

    @Test
    public void descriptionContainsDescriptionsOfSubmatchers() {
        var matcher = isSequence(equalTo("apple"), equalTo("banana"));

        var result = matcher.describe();

        assertEquals("iterable containing in order:\n 0: \"apple\"\n 1: \"banana\"", result.toString());
    }
}

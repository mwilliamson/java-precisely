package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.MatchResult.unmatched;
import static org.zwobble.precisely.Matchers.containsExactly;
import static org.zwobble.precisely.Matchers.equalTo;

public class ContainsExactlyTests {
    @Test
    public void matchesWhenAllSubmatchersMatchOneElementWithNoItemsLeftover() {
        var matcher = containsExactly(equalTo("apple"), equalTo("banana"));

        var result = matcher.match(List.of("banana", "apple"));

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenElementIsMissing() {
        var matcher = containsExactly(equalTo("apple"), equalTo("banana"), equalTo("coconut"));

        var result = matcher.match(List.of("coconut", "apple"));

        assertEquals(unmatched("""
            was missing element:
              "banana"
            These elements were in the iterable, but did not match the missing element:
             * "coconut": was "coconut"
             * "apple": already matched"""), result);
    }

    @Test
    public void mismatchesWhenDuplicateIsMissing() {
        var matcher = containsExactly(equalTo("apple"), equalTo("apple"));

        var result = matcher.match(List.of("apple"));

        assertEquals(unmatched("""
            was missing element:
              "apple"
            These elements were in the iterable, but did not match the missing element:
             * "apple": already matched"""), result);
    }

    @Test
    public void mismatchesWhenElementIsExpectedButIterableIsEmpty() {
        var matcher = containsExactly(equalTo("apple"));

        var result = matcher.match(List.of());

        assertEquals(unmatched("iterable was empty"), result);
    }

    @Test
    public void whenEmptyIterableIsExpectedThenEmptyIterableMatches() {
        var matcher = containsExactly();

        var result = matcher.match(List.of());

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenContainsExtraElement() {
        var matcher = containsExactly(equalTo("apple"));

        var result = matcher.match(List.of("coconut", "apple"));

        assertEquals(unmatched("had extra elements:\n * \"coconut\""), result);
    }

    @Test
    public void whenThereAreZeroSubmatchersThenDescriptionIsOfEmptyIterable() {
        var matcher = containsExactly();

        var result = matcher.describe();

        assertEquals("empty iterable", result);
    }

    @Test
    public void descriptionUsesSingularWhenThereIsOneSubmatcher() {
        var matcher = containsExactly(equalTo("apple"));

        var result = matcher.describe();

        assertEquals("iterable containing 1 element:\n * \"apple\"", result);
    }

    @Test
    public void descriptionContainsDescriptionsOfSubmatchers() {
        var matcher = containsExactly(equalTo("apple"), equalTo("banana"));

        var result = matcher.describe();

        assertEquals("iterable containing these 2 elements in any order:\n * \"apple\"\n * \"banana\"", result);
    }
}

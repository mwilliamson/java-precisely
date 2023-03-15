package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.Assertions.assertUnmatched;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.Matchers.equalTo;
import static org.zwobble.precisely.Matchers.has;

public class HasTests {
    record User(String username) {
    }

    @Test
    public void matchesWhenFeatureHasCorrectValue() {
        var matcher = has("name", User::username, equalTo("bob"));

        var result = matcher.match(new User("bob"));

        assertEquals(matched(), result);
    }

    @Test
    public void explanationOfMismatchContainsMismatchOfFeature() {
        var matcher = has("name", User::username, equalTo("bob"));

        var result = matcher.match(new User("bobbity"));

        assertUnmatched("name mismatched:\n  was \"bobbity\"", result);
    }

    @Test
    public void descriptionContainsDescriptionOfFeature() {
        var matcher = has("name", User::username, equalTo("bob"));

        var result = matcher.describe();

        assertEquals("name:\n  \"bob\"", result.toString());
    }
}

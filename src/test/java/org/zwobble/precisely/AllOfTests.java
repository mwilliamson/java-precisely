package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.MatchResult.unmatched;
import static org.zwobble.precisely.Matchers.*;

public class AllOfTests {
    record User(String username, String emailAddress) {
    }

    @Test
    public void matchesWhenSubmatchersAllMatch() {
        var matcher = allOf(
            has("username", User::username, equalTo("Bob")),
            has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
        );

        var result = matcher.match(new User("Bob", "bob@example.com"));

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenSubmatcherMismatches() {
        var matcher = allOf(
            has("username", User::username, equalTo("Bob")),
            has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
        );

        var result = matcher.match(new User("Bob", "alice@example.com"));

        assertEquals(unmatched("emailAddress mismatched:\n  was \"alice@example.com\""), result);
    }

    @Test
    public void descriptionContainsDescriptionOfSubmatchers() {
        var matcher = allOf(
            has("username", User::username, equalTo("Bob")),
            has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
        );

        var result = matcher.describe();

        assertEquals("""
            all of:
             * username:
                 "Bob"
             * emailAddress:
                 "bob@example.com\"""", result);
    }
}

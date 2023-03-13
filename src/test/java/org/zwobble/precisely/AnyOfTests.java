package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.zwobble.precisely.MatchResult.matched;
import static org.zwobble.precisely.MatchResult.unmatched;
import static org.zwobble.precisely.Matchers.*;

public class AnyOfTests {
    record User(String username, String emailAddress) {
    }

    @Test
    public void matchesWhenSubmatchersAllMatch() {
        var matcher = anyOf(
            has("username", User::username, equalTo("Bob")),
            has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
        );

        var result = matcher.match(new User("Bob", "bob@example.com"));

        assertEquals(matched(), result);
    }

    @Test
    public void matchesWhenAnySubmatchersMatch() {
        var matcher = anyOf(equalTo("bob"), equalTo("jim"));

        var result = matcher.match("bob");

        assertEquals(matched(), result);
    }

    @Test
    public void mismatchesWhenNoSubmatchersMatch() {
        var matcher = anyOf(equalTo("bob"), equalTo("jim"));

        var result = matcher.match("alice");

        assertEquals(unmatched("""
            did not match any of:
             * bob
               was alice
             * jim
               was alice"""), result);
    }

    @Test
    public void descriptionContainsDescriptionOfSubmatchers() {
        var matcher = anyOf(
            has("username", AllOfTests.User::username, equalTo("Bob")),
            has("emailAddress", AllOfTests.User::emailAddress, equalTo("bob@example.com"))
        );

        var result = matcher.describe();

        assertEquals("""
            any of:
             * username:
                 Bob
             * emailAddress:
                 bob@example.com""", result);
    }
}

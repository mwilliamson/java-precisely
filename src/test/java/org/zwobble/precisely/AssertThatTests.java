package org.zwobble.precisely;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.zwobble.precisely.AssertThat.assertThat;
import static org.zwobble.precisely.Matchers.equalTo;

public class AssertThatTests {
    @Test
    public void assertThatReturnsNormallyIfMatcherMatches() {
        assertThat(1, equalTo(1));
    }

    @Test
    public void assertThatThrowsAssertionErrorIfMatchFails() {
        var error = assertThrows(AssertionError.class, () -> assertThat(1, equalTo(2)));

        assertEquals(error.getMessage(), "\nExpected:\n  2\nbut:\n  was 1");
    }
}

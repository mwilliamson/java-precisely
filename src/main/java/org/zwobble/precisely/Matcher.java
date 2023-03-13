package org.zwobble.precisely;

public interface Matcher<T> {
    MatchResult match(T actual);
    String describe();
}

# Precisely: better assertions for Java tests

Precisely allows you to write precise assertions so you only test the behaviour you're really interested in.
This makes it clearer to the reader what the expected behaviour is,
and makes tests less brittle.
This also allows better error messages to be generated when assertions fail.
Inspired by [Hamcrest](http://hamcrest.org).

For instance, suppose we want to make sure that a `unique` function removes duplicates from a list.
We might write a test like so:

```java
import static org.zwobble.precisely.AssertThat.assertThat;
import static org.zwobble.precisely.Matchers.containsExactly;
import static org.zwobble.precisely.Matchers.equalTo;

public class UniqueTests {

    @Test
    public void uniqueRemovesDuplicates() {
        var result = unique(List.of("a", "a", "b", "a", "b"));

        assertThat(result, containsExactly(equalTo("a"), equalTo("b")));
    }   
}
```

The assertion will pass so long as `result` contains `"a"` and `"b"` in any order,
but no other items.
Unlike, say, `assertEquals(result, ["a", "b"])`, our assertion ignores the ordering of elements.
This is useful when:

* the ordering of the result is non-determistic,
  such as the results of SQL SELECT queries without an ORDER BY clause.

* the ordering isn't specified in the contract of `unique`.
  If we assert a particular ordering, then we'd be testing the implementation rather than the contract.

* the ordering is specified in the contract of `unique`,
  but the ordering is tested in a separate test case.

When the assertion fails,
rather than just stating the two values weren't equal,
the error message will describe the failure in more detail.
For instance, if `result` has the value `["a", "a", "b"]`,
we'd get the failure message:

```
Expected: iterable containing in any order:
  * 'a'
  * 'b'
but: had extra elements:
  * 'a'
```

## API

Use `org.zwobble.precisely.AssertThat.assertThat(value, matcher)` to assert that a value satisfies a matcher.

Matchers can be create using static methods on `org.zwobble.precisely.Matchers`:

* `<T> Matcher<T> allOf<T>(Matcher<? super T>... matchers)`,
  `<T> Matcher<T> allOf<T>(List<Matcher<? super T>> matchers)`:
  matches a value if all sub-matchers match.
  For instance:
  
  ```java
  assertThat(result, allOf(
      has("username", User::username, equalTo("Bob")),
      has("emailAddress", User::emailAddress, equalTo("bob@example.com"))
  ));
  ```

* `<T> Matcher<T> anyOf<T>(Matcher<? super T>... matchers)`,
  `<T> Matcher<T> anyOf<T>(List<Matcher<? super T>> matchers)`:
  matches a value if any sub-matchers match.
  For instance:

  ```java
  assertThat(result, anyOf(
      equalTo("Alice"),
      equalTo("Bob")
  ));
  ```

* `Matcher<Object> anything()`: matches all values.

* `<T> Matcher<Iterable<T>> containsExactly<T>(Matcher<? super T>... elements)`,
  `<T> Matcher<Iterable<T>> containsExactly<T>(List<Matcher<? super T>> elements)`:
  matches an iterable if it has the same elements in any order.
  For instance:

  ```java
  assertThat(result, containsExactly(equalTo("a"), equalTo("b")));
  // Matches List.of("a", "b") and List.of("b", "a"),
  // but not List.of("a", "a", "b") nor List.of("a") nor List.of("a", "b", "c")
  ```

* `<T> Matcher<T> equalTo<T>(T value)`: matches a value if it is equal to `value` using `.equals()`.
  For instance:

  ```java
  assertThat(result, equalTo("hello"));
  ```

* `<T, U> Matcher<T> has(String name, Function<T, U> extract, Matcher<? super U> matcher)`:
  matches a value if an extracted subvalue matches the given matcher.
  For instance:

  ```java
  assertThat(result, has("username", User::username, equalTo("Bob")));
  ```

* `<T, U> Matcher<U> instanceOf(Class<T> clazz)`:
  matches a value if it is an instance of the given class.
  For instance:

  ```java
  assertThat(result, instanceOf(User.class));
  ```

* `<T, U> Matcher<U> instanceOf(Class<T> clazz, Matcher<? super T>... matchers)`,
  `<T, U> Matcher<U> instanceOf(Class<T> clazz, List<Matcher<? super T>> matchers)`:
  matches a value if it is an instance of the given class, and if all sub-matchers match.
  For instance:

  ```java
  assertThat(result, instanceOf(
      User.class,
      has("username", User::username, equalTo("Bob"))
  ));
  ```

* `Matcher<Iterable<T>> isSequence<T>(Matcher<? super T>... elements)`,
  `Matcher<Iterable<T>> isSequence<T>(List<Matcher<? super T>> elements)`:
  matches an iterable if it has the same elements in the same order.
  For instance:

  ```java
  assertThat(result, isSequence(equalTo("a"), equalTo("b")));
  // Matches List.of("a", "b")
  // but not List.of("b", "a") nor List.of("a", "b", "c") nor List.of("c", "a", "b")
  ```

* `Matcher<T> not<T>(Matcher<? super T> matcher)`: negates a matcher.
  For instance:

  ```java
  assertThat(result, not(equalTo("hello")));
  ```

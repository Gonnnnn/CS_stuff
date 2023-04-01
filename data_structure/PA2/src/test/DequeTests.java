import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

class DequeTests {

    @Test
    @Score(2)
    void testInsert()
    {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IDeque<Integer> dq = new Deque<>();

                dq.insertFirst(1);
                assertThat(dq.first(), is(1));

                dq.insertLast(2);
                assertThat(dq.last(), is(2));
            });
    }

    @Test
    @Score(2)
    void testDelete()
    {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IDeque<Integer> dq = new Deque<>();

                dq.insertFirst(1);
                dq.insertLast(2);

                dq.deleteLast();
                assertThat(dq.last(), is(1));

                dq.deleteFirst();
                assertThat(dq.isEmpty(), is(true));
            });
    }

    @Test
    @Score(1)
    void testEmpty()
    {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IDeque<Integer> dq = new Deque<>();

                assertThrows(
                    IllegalStateException.class, () -> dq.deleteFirst(),
                    "deleteFirst()");
                assertThrows(
                    IllegalStateException.class, () -> dq.deleteLast(),
                    "deleteLast()");
                assertThrows(
                    IllegalStateException.class, () -> dq.first(),
                    "first().");
                assertThrows(
                    IllegalStateException.class, () -> dq.last(),
                    "last()");
                assertThat("Deque size", dq.size(), is(0));
                assertThat(dq.isEmpty(), is(true));
            });
    }
}

import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

class HeapTests {

    @Test
    @Score(1)
    void testConstruction() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IHeap<String> heap = new Heap<String>();
                heap.bottomUp(new int[]{ 1, 2 }, new String[]{ "hello", "world" });

                assertThat(heap.size(), is(2));
                assertThat(heap.min(), is("hello"));
            });
    }

    @Test
    @Score(1)
    void testInsert() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IHeap<String> heap = new Heap<>();

                heap.insert(1, "hello");
                heap.insert(2, "world");

                assertThat(heap.size(), is(2));
            });
    }

    @Test
    @Score(1)
    void testGetRoot() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IHeap<String> heap = new Heap<>();

                heap.insert(1, "hello");
                heap.insert(2, "world");

                assertThat(heap.getRoot().getValue(), is("hello"));
            });
    }

    @Test
    @Score(1)
    void testRemoveMin() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IHeap<String> heap = new Heap<>();

                heap.insert(1, "hello");
                heap.insert(2, "world");

                assertThat(heap.size(), is(2));

                assertThat(heap.removeMin(), is("hello"));
                assertThat(heap.removeMin(), is("world"));

                assertThat(heap.isEmpty(), is(true));
            });
    }

    @Test
    @Score(1)
    void testMin() {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IHeap<String> heap = new Heap<>();

                heap.insert(1, "hello");
                heap.insert(2, "world");

                assertThat(heap.size(), is(2));

                assertThat(heap.min(), is("hello"));
                assertThat(heap.min(), is("hello"));

                assertThat(heap.size(), is(2));
            });
    }
}

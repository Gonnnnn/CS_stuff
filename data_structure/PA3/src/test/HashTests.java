import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.Arrays;
import java.util.List;

public class HashTests {
    private class HashFn1 implements IHashFunction<Integer> {
        public int hash(Integer i, int length) {
            return (i % length);
        }
    }

    private class ResizeFn1 implements IResizeFunction {
        public boolean checkResize(int tablesize, int numItems) {
            if (numItems > tablesize * 0.8) {
                return true;
            }
            return false;
        }

        public int extendTable(int tablesize) {
            return tablesize * 2;
        }
    }

    @Test
    @Score(1)
    public void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new Hash<Integer>(10, new HashFn1(), new ResizeFn1());
            assertThat(h.tablesize(), is(10));
        });
    }

    @Test
    @Score(1)
    public void testHashingOrigin() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new Hash<Integer>(10);
            assertThat(h.tablesize(), is(10));

            h.put(6);
            h.put(1);
            assertThat(h.size(), is(2));
            assertThat(h.exists(6), is(true));
            assertThat(h.exists(2), is(false));

            assertThrows(IllegalStateException.class, () -> h.remove(8));
        });
    }

    @Test
    @Score(1)
    public void testHashingSimple() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new Hash<Integer>(4, new HashFn1(), new ResizeFn1());
            assertThat(h.tablesize(), is(4));

            h.put(3);
            h.put(2);
            assertThat(h.size(), is(2));
            assertThat(h.exists(3), is(true));

            h.remove(2);
            assertThat(h.exists(2), is(false));

            assertThat(h.show(), is(Arrays.asList(new Integer[] { null, null, null, 3 })));
        });
    }

    @Test
    @Score(1)
    public void testHashingCollision() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new Hash<Integer>(5, new HashFn1(), new ResizeFn1());
            assertThat(h.tablesize(), is(5));

            h.put(0);
            h.put(5);
            h.put(3);

            assertThat(h.size(), is(3));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 0, 5, null, 3, null })));

        });
    }

    @Test
    @Score(1)
    public void testHashingRehashing() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IHash<Integer> h = new Hash<Integer>(5, new HashFn1(), new ResizeFn1());
            assertThat(h.tablesize(), is(5));

            h.put(0);
            h.put(5);
            h.put(3);
            h.put(13);
            h.put(2);

            assertThat(h.tablesize(), is(10));
            assertThat(h.size(), is(5));

            h.put(7);
            h.put(9);
            assertThat(h.size(), is(7));
            assertThat(h.show(), is(Arrays.asList(new Integer[] { 0, null, 2, 3, 13, 5, null, 7, null, 9 })));

        });
    }
}

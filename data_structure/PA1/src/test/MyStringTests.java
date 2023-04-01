import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

import java.util.Arrays;

public class MyStringTests {
    @Test
    @Score(1)
    void testCreation() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IString s = new MyString();
            assertThat(s.print(), is(new char[] { }));
            assertThat(s.size(), is(0));
        });
    }

    @Test
    @Score(1)
    public void testInsert() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IString s = new MyString();
            s.append('a');
            s.append('e');
            assertThat(s.print(), is(new char[] { 'a','e' }));
            s.prepend('d');
            assertThat(s.print(), is(new char[] { 'd', 'a','e' }));
        });
    }

    @Test
    @Score(1)
    public void testHeadTail() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IString s = new MyString();
            s.append('a');
            s.append('c');
            s.prepend('e');
            
            assertThat(s.head().value(), is('e'));
            assertThat(s.tail().value(), is('c'));
        });
    }

    @Test
    @Score(1)
    public void testFind() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IString s = new MyString();
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            s.append('a');
            assertThat(s.findFirst('a'), is(0));
            assertThat(s.findLast('a'), is(9));
            assertThat(s.findFirst('b'), is(-1));
            assertThat(s.findLast('b'), is(-1));
        });
    }

    @Test
    @Score(1)
    public void testIntersection() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            IString s1 = new MyString();
            IString s2 = new MyString();
            s1.append('a');
            s1.append('a');
            s1.append('a');
            s1.append('a');
            s2.append('a');
            s2.append('a');
            s2.append('b');
            s2.append('a');
            assertThat(s1.lessOrEqual(s2),is(true));
        });
    }
}
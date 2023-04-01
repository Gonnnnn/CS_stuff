import java.time.Duration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import testrunner.annotation.Score;

class ReconstructorTests {

    @Test
    @Score(1)
    void testInsert()
    {
        assertTimeoutPreemptively(
            Duration.ofSeconds(1), () -> {
                IReconstructor tb = new Reconstructor();

                ITreeNode root = tb.reconstruct(new Integer[] {4,5,2,6,3,1},new Integer[] {4,2,5,1,3,6});
                assertThat(root.val(), is(1));
                assertThat(root.left().val(), is(2));
                assertThat(root.right().val(), is(3));
                assertThat(root.left().left().val(), is(4));
                assertThat(root.left().right().val(), is(5));
                assertThat(root.right().right().val(), is(6));
                assertThat(root.right().left(), is(nullValue()));
                assertThat(root.parent(), is(nullValue()));
                assertThat(root.right().parent(), is(root));
            });
    }
}

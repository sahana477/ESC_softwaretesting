
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;




public class GroupJonahTest {
    public GroupJonahTest() {
    }

    @Test
    public void testingFileExceptions() {

        try {
            GroupJonah.main(new String[]{});
        } catch (Throwable e) {
            assertTrue(e instanceof FileNotFoundException);
        }

    }

    @Test
    public void testingRuntimeExceptions() {

        try {
            GroupJonah.main(new String[]{});
        } catch (Throwable e) {
            assertTrue(e instanceof RuntimeException);
        }

    }

    @Test
    public void testingExceptions() {

        try {
            GroupJonah.main(new String[]{});
        } catch (Throwable e) {
            assertTrue(e instanceof Exception);
        }

    }
}

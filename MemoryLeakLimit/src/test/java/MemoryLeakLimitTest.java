import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.naming.LimitExceededException;

import static org.junit.Assert.*;

/**
 * Created by user on 04.05.2017.
 */
public class MemoryLeakLimitTest {

    @Rule
    public MemoryLeakLimit leakLimit = new MemoryLeakLimit();
    private byte[] bytes;

    @Before
    public void initialize() {
        bytes = null;
    }

    @Test
    public void outLimit() {
        leakLimit.limit(1);
        bytes = new byte[2_000_000];
    }

    @Test
    public void inLimit() {
        leakLimit.limit(1);
        bytes = new byte[1_000];
    }

}
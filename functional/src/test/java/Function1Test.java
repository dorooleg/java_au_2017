import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by user on 05.04.2017.
 */
public class Function1Test {

    @Test
    public void compose() {
        Function1<Integer, Integer> mul2 = value -> value << 1;
        Function1<Integer, Integer> inc = value -> 1 + value;
        assertEquals(5, (int) mul2.compose(inc).apply(2));
        assertEquals(6, (int) inc.compose(mul2).apply(2));
        Function1<Integer, Integer> id = x -> x;
        Function1<Object, Boolean> greater10 = x -> ((Integer)x).compareTo(10) > 0;
        assertFalse(id.compose(greater10).apply(10));
    }

}
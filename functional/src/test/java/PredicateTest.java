import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 06.04.2017.
 */
public class PredicateTest {

    @Test
    public void or() {
        Predicate<Object> trueP = Predicate.ALWAYS_TRUE;
        Predicate<Object> falseP = Predicate.ALWAYS_FALSE;
        assertTrue(trueP.or(falseP).test(null));
        assertTrue(falseP.or(trueP).test(null));
        assertTrue(trueP.or(trueP).test(null));
        assertFalse(falseP.or(falseP).test(null));
        Predicate<String> equals10 = x -> x.equals("10");
        assertFalse(equals10.or(x -> x.equals("15")).test("0"));
    }

    @Test
    public void and() {
        Predicate<Object> trueP = Predicate.ALWAYS_TRUE;
        Predicate<Object> falseP = Predicate.ALWAYS_FALSE;
        assertFalse(trueP.and(falseP).test(null));
        assertFalse(falseP.and(trueP).test(null));
        assertTrue(trueP.and(trueP).test(null));
        assertFalse(falseP.and(falseP).test(null));
        Predicate<String> equals0 = x -> x.equals("0");
        assertTrue(equals0.and(x -> x.equals("0")).test("0"));
    }

    @Test
    public void not() {
        Predicate<Integer> greater9 = x -> x > 9;
        assertTrue(greater9.test(10));
        assertFalse(greater9.not().test(10));
    }
}
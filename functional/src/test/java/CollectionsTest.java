import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 06.04.2017.
 */
public class CollectionsTest {

    @Test
    public void map() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        Function1<Integer, Integer> inc = value -> 1 + value;
        assertEquals(Arrays.asList(2, 3, 4), Collections.map(inc, list));

        Function1<Object, String> toString = v -> v.toString();
        assertEquals(Arrays.asList("1", "2", "3"), Collections.map(toString, list));
    }

    @Test
    public void filter() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Predicate<Integer> greater3 = x -> x.compareTo(3) > 0;
        assertEquals(Arrays.asList(4, 5, 6), Collections.filter(greater3, list));

        Predicate<Object> greater3Obj = x -> x.toString().compareTo("3") > 0;
        assertEquals(Arrays.asList(4, 5, 6), Collections.filter(greater3Obj, list));
    }

    @Test
    public void takeWhile() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Predicate<Integer> less3 = x -> x.compareTo(3) < 0;
        assertEquals(Arrays.asList(1, 2), Collections.takeWhile(less3, list));

        Predicate<Object> less3Obj = x -> x.toString().compareTo("3") < 0;
        assertEquals(Arrays.asList(1, 2), Collections.takeWhile(less3Obj, list));
    }

    @Test
    public void takeUnless() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Predicate<Integer> greater3 = x -> x.compareTo(3) > 0;
        assertEquals(Arrays.asList(1, 2, 3), Collections.takeUnless(greater3, list));

        Predicate<Object> greater3Obj = x -> x.toString().compareTo("3") > 0;
        assertEquals(Arrays.asList(1, 2, 3), Collections.takeUnless(greater3Obj, list));
    }


    @Test
    public void foldl() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5);
        Function2<Double, Integer, Double> diff = (l, r) -> l - r;
        assertEquals(-11, Collections.foldl(diff, 0.0, list).intValue());

        Function2<Object, Object, String> toString = (x, y) -> x.toString() + y.toString();
        assertEquals("1235", Collections.foldl(toString, "", list));
    }

    @Test
    public void foldr() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5);
        Function2<Integer, Double, Double> d = (l, r) -> l - r;
        assertEquals(-3, Collections.foldr(d, 0.0, list).intValue());

        Function2<Object, Object, String> toString = (x, y) -> x.toString() + y.toString();
        assertEquals("1235", Collections.foldr(toString, "", list));
    }

}
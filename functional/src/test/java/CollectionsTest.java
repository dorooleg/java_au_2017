import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Created by user on 06.04.2017.
 */
public class CollectionsTest {

    @Test
    public void map() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Function1<Integer, Integer> inc = value -> 1 + value;
        assertEquals(Arrays.asList(2, 3, 4), Collections.map(inc, list));
    }

    @Test
    public void filter() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Predicate<Integer> greater3 = x -> x.compareTo(3) > 0;
        assertEquals(Arrays.asList(4, 5, 6), Collections.filter(greater3, list));
    }

    @Test
    public void takeWhile() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Predicate<Integer> less3 = x -> x.compareTo(3) < 0;
        assertEquals(Arrays.asList(1, 2), Collections.takeWhile(less3, list));
    }

    @Test
    public void takeUnless() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Predicate<Integer> greater3 = x -> x.compareTo(3) > 0;
        assertEquals(Arrays.asList(1, 2, 3), Collections.takeUnless(greater3, list));
    }

    @Test
    public void foldl() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 5));
        Function2<Double, Integer, Double> diff = (l, r) -> l - r;
        assertEquals(-11, Collections.foldl(diff, 0.0, list).intValue());
    }

    @Test
    public void foldr() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 5));
        Function2<Integer, Double, Double> d = (l, r) -> l - r;
        assertEquals(-3, Collections.foldr(d, 0.0, list).intValue());
    }

}
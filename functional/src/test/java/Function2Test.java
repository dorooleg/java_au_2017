import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 06.04.2017.
 */
public class Function2Test {

    @Test
    public void compose() {
        Function2<Double, Double, Double> sum = (a, b) -> a + b;
        Function1<Double, Integer> mul2 = value -> value.intValue() << 1;
        assertEquals(6, sum.compose(mul2).apply(1.0, 2.0).intValue());
        Function1<Object, Integer> toInteger = x -> ((Double)x).intValue();
        assertEquals(new Integer(2), sum.compose(toInteger).apply(1.2, 1.3));
    }

    @Test
    public void bind1() {
        Function2<Integer, Integer, Integer> div = (a, b) -> a / b;
        Function1<Integer, Integer> div80 = div.bind1(80);
        assertEquals(20, div80.apply(4).intValue());
    }

    @Test
    public void bind2() {
        Function2<Integer, Integer, Integer> div = (a, b) -> a / b;
        Function1<Integer, Integer> div2 = div.bind2(2);
        assertEquals(40, div2.apply(80).intValue());
    }

    @Test
    public void curry() {
        Function2<Double, Double, Double> sum = (a, b) -> a + b;
        Function1<Double, Double> plus5 = sum.curry().apply(5.0);
        assertEquals(12, plus5.apply(7.0).intValue());
    }

}
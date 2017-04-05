/**
 * Created by user on 05.04.2017.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Collections {

    private Collections() {
    }

    public static <T, R> List<R> map(Function1<? super T, ? extends R> f, Iterable<T> iterable) {
        List<R> result = new ArrayList<>();
        for (T elem : iterable) {
            result.add(f.apply(elem));
        }
        return result;
    }

    public static <T> List<T> filter(Predicate<? super T> predicate, Iterable<T> iterable) {
        List<T> result = new ArrayList<>();
        for (T elem : iterable) {
            if (predicate.test(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

    public static <T> List<T> takeWhile(Predicate<? super T> predicate, Iterable<T> iterable) {
        List<T> result = new ArrayList<>();
        for (T elem : iterable) {
            if (!predicate.test(elem)) {
                return result;
            }
            result.add(elem);
        }
        return result;
    }

    public static <T> List<T> takeUnless(Predicate<? super T> predicate, Iterable<T> iterable) {
        return takeWhile(predicate.not(), iterable);
    }

    public static <T, R> R foldl(Function2<? super R, ? super T, ? extends R> f, R result,
                                 Iterable<T> iterable) {
        for (T elem : iterable) {
            result = f.apply(result, elem);
        }
        return result;
    }

    public static <T, R> R foldr(Function2<? super T, ? super R, ? extends R> function, R ini,
                                 Iterable<T> iterable) {
        return foldr(function, ini, iterable.iterator());
    }

    private static <T, R> R foldr(Function2<? super T, ? super R, ? extends R> f, R result, Iterator<T> iterator) {
        return !iterator.hasNext() ? result : f.apply(iterator.next(), foldr(f, result, iterator));
    }

}

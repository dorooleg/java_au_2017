/**
 * Created by user on 05.04.2017.
 */

public interface Predicate<T> {

    Predicate<Object> ALWAYS_TRUE = v -> true;
    Predicate<Object> ALWAYS_FALSE = v -> false;

    boolean test(T t);

    default Predicate<T> and(Predicate<? super T> other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return (t) -> test(t) && other.test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return (t) -> test(t) || other.test(t);
    }

    default Predicate<T> not() {
        return (t) -> !test(t);
    }
}

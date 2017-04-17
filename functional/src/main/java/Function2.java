/**
 * Created by user on 05.04.2017.
 */
public interface Function2<T, U, R> {

    R apply(T t, U u);

    default <P> Function2<T, U, P> compose(Function1<? super R, ? extends P> before) {
        if (before == null) {
            throw new IllegalArgumentException();
        }
        return (v, p) -> before.apply(apply(v, p));
    }

    default Function1<U, R> bind1(T t) {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        return u -> apply(t, u);
    }

    default Function1<T, R> bind2(U u) {
        if (u == null) {
            throw new IllegalArgumentException();
        }
        return t -> apply(t, u);
    }

    default Function1<T, Function1<U, R>> curry() {
        return t -> u -> apply(t, u);
    }

}

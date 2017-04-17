/**
 * Created by user on 05.04.2017.
 */
public interface Function1<T, R> {

    R apply(T t);

    default <P> Function1<T, P> compose(Function1<? super R, ? extends P> g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        return v -> g.apply(apply(v));
    }

}

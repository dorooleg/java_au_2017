import java.util.*;
import java.util.function.Function;

/**
 * Created by user on 07.04.2017.
 */
public class HashMultiset<E>  extends AbstractSet<E> implements Multiset<E> {

    private final LinkedHashMap<E, Integer> hashMap = new LinkedHashMap<>();
    private int size = 0;

    @Override
    public int count(Object element) {
        return hashMap.getOrDefault(element, 0);
    }

    @Override
    public Set<E> elementSet() {
        return hashMap.keySet();
    }

    @Override
    public Set<Entry<E>> entrySet() {
        return new AbstractSet<Entry<E>>() {

            private Iterator<Map.Entry<E, Integer>> iterator = hashMap.entrySet().iterator();

            @Override
            public Iterator<Entry<E>> iterator() {
                return new Iterator<Entry<E>>() {

                    private int countElements = 0;

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Entry<E> next() {
                        return new Entry<E>() {

                            private final Map.Entry<E, Integer> next = iterator.next();

                            {
                                countElements = next.getValue();
                            }

                            @Override
                            public E getElement() {
                                return next.getKey();
                            }

                            @Override
                            public int getCount() {
                                return next.getValue();
                            }
                        };
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                        size -= countElements;
                    }

                };
            }

            @Override
            public int size() {
                return hashMap.size();
            }
        };
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private boolean deleted = false;
            private E currentElement = null;
            private int countElements = 0;
            private Iterator<Map.Entry<E, Integer>> iterator = hashMap.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return countElements > 0 || iterator.hasNext();
            }

            @Override
            public E next() {
                deleted = false;
                if (countElements == 0) {
                    Map.Entry<E, Integer> next = iterator.next();
                    countElements = next.getValue();
                    currentElement = next.getKey();
                }

                countElements--;
                return currentElement;
            }

            @Override
            public void remove() {
                if (deleted) {
                    throw new IllegalStateException();
                }

                deleted = true;

                removeInternal(currentElement, v -> {
                    iterator.remove();
                    return null;
                });
            }
        };
    }

    @Override
    public boolean add(E e) {
        final int count = hashMap.getOrDefault(e, 0) + 1;
        hashMap.put(e, count);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return removeInternal(o, v -> {
            hashMap.remove(o);
            return null;
        });
    }

     private boolean removeInternal(Object o, Function<Void, Void> removeFunction) {
         final int count = hashMap.getOrDefault(o, 0);
         if (count == 0) {
             return false;
         }

         if (count == 1) {
             removeFunction.apply(null);
         } else {
             hashMap.put((E) o, count - 1);
         }
         size--;
         return true;
    }

    @Override
    public void clear() {
        hashMap.clear();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }
}
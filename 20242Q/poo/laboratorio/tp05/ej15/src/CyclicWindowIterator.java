import java.util.Iterator;
import java.util.NoSuchElementException;

public class CyclicWindowIterator<E> implements Iterator<E[]> { // !!!

    private final E[] collection;
    private int windowDim, index;

    public CyclicWindowIterator(E[] collection, int windowDim) {
        this.collection = collection;
        this.windowDim = windowDim;
    }

    @Override
    public boolean hasNext() {
        return collection.length != 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        E[] window = (E[]) new Object[windowDim]; // !! pues no se puede crear Array de E generic
        for(int i = 0; i < windowDim; i++) {
            window[i] = collection[(index + i) % collection.length];
        }
        index++;
        return window;
    }
}

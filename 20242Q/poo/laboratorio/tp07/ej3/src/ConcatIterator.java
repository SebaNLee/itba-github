import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcatIterator<T> implements Iterator<T> {

    // !! buenos nombres
    private Iterator<T> currentIterator;
    private Iterator<T> secondIterator;

    public ConcatIterator(Iterator<T> iter1, Iterator<T> iter2) {
        currentIterator= iter1;
        secondIterator= iter2;
    }

    @Override
    public boolean hasNext() {

        // !! chequeo si estoy en el caso de sin elementos del primer iterador
        if(!currentIterator.hasNext() && currentIterator != secondIterator) {
            currentIterator = secondIterator;
        }

        return currentIterator.hasNext();

    }

    @Override
    public T next() {

        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        return currentIterator.next();

    }
}

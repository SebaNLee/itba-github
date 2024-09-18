import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedIterator<E> implements Iterator<E> {

    private E[] array;
    private int index;

    public ReversedIterator(E[] array) {
        this.array = array;
        index = array.length - 1; // !!
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public E next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        return array[index--];
    }
}

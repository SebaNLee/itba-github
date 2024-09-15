package ar.edu.itba.poo.soltp5.ej14;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedIterator<E> implements Iterator<E> {

    private int index;
    private final E[] collection;

    public ReversedIterator(E[] collection) {
        this.collection = collection;
        this.index = collection.length - 1;
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
        return collection[index--];
    }

}

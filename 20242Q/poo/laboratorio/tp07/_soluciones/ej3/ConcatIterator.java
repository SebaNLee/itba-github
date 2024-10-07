package ar.edu.itba.poo.soltp7.ej3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcatIterator<E> implements Iterator<E> {

    private Iterator<E> currentIterator, nextIterator;

    public ConcatIterator(Iterator<E> leftIterator, Iterator<E> rightIterator) {
        this.currentIterator = leftIterator;
        this.nextIterator = rightIterator;
    }

    @Override
    public boolean hasNext() {
        if(!currentIterator.hasNext() && currentIterator != nextIterator) {
            currentIterator = nextIterator;
        }
        return currentIterator.hasNext();
    }

    @Override
    public E next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        return currentIterator.next();
    }

}

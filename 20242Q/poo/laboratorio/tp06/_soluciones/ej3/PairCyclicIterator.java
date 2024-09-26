package ar.edu.itba.poo.soltp6.ej3;

import java.util.Iterator;

public class PairCyclicIterator<E> implements Iterator<Pair<E>> {

    private final Iterable<E> collection;
    private Iterator<E> iterator;

    PairCyclicIterator(Iterable<E> collection) {
        this.collection = collection;
        this.iterator = collection.iterator();
    }

    @Override
    public boolean hasNext() {
        return this.collection.iterator().hasNext();
    }

    @Override
    public Pair<E> next() {
        E left, right;
        if(!iterator.hasNext()) {
            iterator = collection.iterator();
        }
        left = iterator.next();
        if(!iterator.hasNext()) {
            iterator = collection.iterator();
        }
        right = iterator.next();
        return new Pair<>(left, right);
    }

}

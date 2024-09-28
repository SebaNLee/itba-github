import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PairCyclicIterator<T> implements Iterator<Pair<T>> { // !! el Iterator<> debe ser consistente con lo que devuelve next()

    private Collection<T> collection;
    private Iterator<T> iterator;

    PairCyclicIterator(Collection<T> collection) {
        this.collection = collection;
        this.iterator = collection.iterator();
    }

    @Override
    public boolean hasNext() {

        // !! se usa el iterador que tiene la colección por default, NO el guardado (puede ser el último elemento)
        return collection.iterator().hasNext();
    }

    @Override
    public Pair<T> next() {
        
        // confimo que por lo menos tenga 1 elemento
        if(!hasNext()) {
            throw new NoSuchElementException();
        }

        T left, right;

        // chequeo si no es el final del iterador y, si lo es, reinicio el iterador
        if(!iterator.hasNext()) {
            iterator = collection.iterator();
        }

        left = iterator.next();

        // ídem, este chequeo sirve tanto para el caso en el que es el último elem, o es solo un elem
        if(!iterator.hasNext()) {
            iterator = collection.iterator();
        }

        right = iterator.next();

        return new Pair<T>(left, right);
    }

}

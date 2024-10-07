import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MultiSortedCollectionImpl<T> implements MultiSortedCollection<T> {

    private Map<Comparator<T>, Set<T>> collection = new HashMap<>(); // !!

    @Override
    public void add(Comparator<T> comp) {

        collection.put(comp, new TreeSet<>(comp)); // !!!

        return;
    }

    @Override
    public void add(T elem) {
        
        if(collection.isEmpty()) { // !! caso borde: no hay comparadores
            throw new IllegalStateException();
        }

        for(Set<T> set : collection.values()) { // !!
            set.add(elem);
        }

        return;
    }

    @Override
    public void remove(T elem) {

        for(Set<T> set : collection.values()) {
            set.remove(elem);
        }
    }

    @Override
    public Iterable<T> iterable(Comparator<T> comp) {

        if(!collection.containsKey(comp)) {
            throw new IllegalArgumentException();
        }

        return collection.get(comp);
    }

}

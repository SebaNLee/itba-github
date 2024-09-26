package ar.edu.itba.poo.soltp6.ej10;

import java.util.*;

public class MultiSortedCollectionImpl<T> implements MultiSortedCollection<T> {

    private Set<T> data = new HashSet<>();
    private Map<Comparator<T>, Set<T>> multiSortedData = new HashMap<>();

    @Override
    public void add(Comparator<T> comparator) {
        TreeSet<T> newSet = new TreeSet<>(comparator);
        newSet.addAll(data);
        multiSortedData.put(comparator, newSet);
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        multiSortedData.values().forEach(set -> set.add(elem));
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        if(!multiSortedData.isEmpty()) {
            multiSortedData.values().forEach(set -> set.remove(elem));
        }
    }

    @Override
    public Iterable<T> iterable(Comparator<T> comp) {
        if(!multiSortedData.containsKey(comp)) {
            throw new IllegalStateException();
        }
        return multiSortedData.get(comp);
    }

}

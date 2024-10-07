package ar.edu.itba.poo.soltp7.ej7;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SortedMapImpl<K extends Comparable<K>,V> extends TreeMap<K,V> implements SortedMap<K,V> {

    @Override
    public Iterator<Map.Entry<K,V>> iterator() {
        return entrySet().iterator();
    }

    @Override
    public K firstKey() {
        return super.firstKey();
    }

    @Override
    public V firstValue() {
        return get(firstKey());
    }

}

package ar.edu.itba.poo.soltp7.ej5;

import ar.edu.itba.poo.soltp6.ej8v2.Bag;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BagImpl<T> implements Bag<T> {

    protected Map<T, Integer> values = new HashMap<>();

    @Override
    public void add(T elem) {
        values.putIfAbsent(elem, 0);
        values.merge(elem, 1, Integer::sum);
    }

    @Override
    public boolean contains(T elem) {
        return values.containsKey(elem);
    }

    @Override
    public int count(T elem) {
        return values.getOrDefault(elem, 0);
    }

    @Override
    public void remove(T elem) {
        if (!values.containsKey(elem)) {
            throw new NoSuchElementException();
        }
        if (values.get(elem) == 1) {
            values.remove(elem);
        } else {
            values.merge(elem, -1, Integer::sum);
        }
    }

    @Override
    public int size() {
        int total = 0;
        for(int i : values.values()) {
            total += i;
        }
        return total;
    }

    @Override
    public int sizeDistinct() {
        return values.size();
    }

}

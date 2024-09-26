package ar.edu.itba.poo.soltp6.ej8;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BagImpl<T> implements Bag<T> {

    private Map<T, Integer> values = new HashMap<>();

    @Override
    public void add(T elem) {
        if (!values.containsKey(elem)) {
            values.put(elem, 1);
        } else {
            values.put(elem, values.get(elem) + 1);
        }
    }

    @Override
    public boolean contains(T elem) {
        return values.containsKey(elem);
    }

    @Override
    public int count(T elem) {
        if (!values.containsKey(elem)) {
            return 0;
        }
        return values.get(elem);
    }

    @Override
    public void remove(T elem) {
        if (!values.containsKey(elem)) {
            throw new NoSuchElementException();
        }
        if (values.get(elem) == 1) {
            values.remove(elem);
        } else {
            values.put(elem, values.get(elem) - 1);
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

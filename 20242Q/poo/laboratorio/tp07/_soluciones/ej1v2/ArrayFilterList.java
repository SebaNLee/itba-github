package ar.edu.itba.poo.soltp7.ej1v2;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayFilterList<T> extends ArrayList<T> implements FilterList<T> {

    public FilterList<T> filter(Predicate<T> criteria) {
        FilterList<T> result = new ArrayFilterList<>();
        for(T elem : this) {
            if (criteria.test(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

}

package ar.edu.itba.poo.soltp7.ej1v1;

import java.util.ArrayList;

public class ArrayFilterList<T> extends ArrayList<T> implements FilterList<T> {

    public FilterList<T> filter(Criteria<T> criteria) {
        FilterList<T> result = new ArrayFilterList<>();
        for(T elem : this) {
            if (criteria.satisfies(elem)) {
                result.add(elem);
            }
        }
        return result;
    }

}

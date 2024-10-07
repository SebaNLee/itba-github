package ar.edu.itba.poo.soltp7.ej1v2;

import java.util.List;
import java.util.function.Predicate;

public interface FilterList<T> extends List<T> {

    FilterList<T> filter(Predicate<T> criteria);

}

package ar.edu.itba.poo.soltp7.ej1v1;

import java.util.List;

public interface FilterList<T> extends List<T> {

    FilterList<T> filter(Criteria<T> criteria);

}

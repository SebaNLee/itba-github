package ar.edu.itba.poo.soltp7.ej2v1;

import java.util.List;

public interface SimpleList<E> extends List<E> {

    <T> T reduce(T initialValue, Reducer<E, T> reducer);

}

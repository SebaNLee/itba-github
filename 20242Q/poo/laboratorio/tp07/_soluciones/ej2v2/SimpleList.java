package ar.edu.itba.poo.soltp7.ej2v2;

import java.util.List;
import java.util.function.BiFunction;

public interface SimpleList<E> extends List<E> {

    <T> T reduce(T initialValue, BiFunction<T,E,T> reducer);

}

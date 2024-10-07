package ar.edu.itba.poo.soltp7.ej2v2;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class SimpleArrayList<E> extends ArrayList<E> implements SimpleList<E> {

    @Override
    public <T> T reduce(T initialValue, BiFunction<T,E,T> reducer) {
        T toReturn = initialValue;
        for(E element : this) {
            toReturn = reducer.apply(toReturn, element);
        }
        return toReturn;
    }

}

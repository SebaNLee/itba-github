package ar.edu.itba.poo.soltp7.ej2v1;

import java.util.ArrayList;

public class SimpleArrayList<E> extends ArrayList<E> implements SimpleList<E> {

    @Override
    public <T> T reduce(T initialValue, Reducer<E, T> reducer) {
        T toReturn = initialValue;
        for(E element : this) {
            toReturn = reducer.reduce(toReturn, element);
        }
        return toReturn;
    }

}

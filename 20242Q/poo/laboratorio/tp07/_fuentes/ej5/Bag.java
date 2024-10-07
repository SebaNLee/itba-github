package ar.edu.itba.poo.tp7.ej5;

public interface Bag<T> {

    void add(T elem);

    void remove(T elem);

    int count(T elem);

    boolean contains(T elem);

    int size();

    int sizeDistinct();

}

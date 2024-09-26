package ar.edu.itba.poo.soltp6.ej8v2;

public interface Bag<T> {

    void add(T elem);

    void remove(T elem);

    int count(T elem);

    boolean contains(T elem);

    int size();

    int sizeDistinct();

}

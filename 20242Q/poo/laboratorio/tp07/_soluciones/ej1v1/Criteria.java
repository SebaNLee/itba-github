package ar.edu.itba.poo.soltp7.ej1v1;

@FunctionalInterface
public interface Criteria<T> {

    boolean satisfies(T obj);

}

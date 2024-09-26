package ar.edu.itba.poo.soltp6.ej7;

import java.util.Collection;

public class Ej7  {

    public static <T extends Comparable<? super T>> T mayor(Collection<? extends T> valores) {
        if (valores.isEmpty()) {
            return null;
        }
        T mayor = null;
        for (T elem : valores) {
            if (mayor == null || mayor.compareTo(elem) < 0) {
                mayor = elem;
            }
        }
        return mayor;
    }

}


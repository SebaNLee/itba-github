package ar.edu.itba.poo.soltp5.ej10;

import java.util.Objects;

public class Pair<A extends Comparable<? super A>, B extends Comparable<? super B>> implements Comparable<Pair<A,B>> {

    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(a, b);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pair<?, ?> pair &&
                a.equals(pair.a) &&
                b.equals(pair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        int aDiff = a.compareTo(o.a);
        if(aDiff != 0) {
            return aDiff;
        }
        return b.compareTo(o.b);
    }

}

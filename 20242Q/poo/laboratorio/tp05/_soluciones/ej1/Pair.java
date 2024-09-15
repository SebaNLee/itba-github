package ar.edu.itba.poo.soltp5.ej1;

import java.util.Objects;

public class Pair<A, B> {

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

}

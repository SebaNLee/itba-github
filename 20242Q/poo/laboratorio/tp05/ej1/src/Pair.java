import java.util.Objects;

public class Pair<A, B> {

    private A a;
    private B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Pair<?, ?> item && // !! wilcard, para cualquier tipo de Pair<A, B>
                a.equals(item.a) &&
                b.equals(item.b);
    }

    @Override
    public String toString() {
        return "[ %s, %s ]".formatted(a, b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}

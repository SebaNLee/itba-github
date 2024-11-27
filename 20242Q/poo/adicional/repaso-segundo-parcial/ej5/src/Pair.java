import java.util.Objects;

public class Pair<T, S> {

    private final T key1;
    private final S key2;

    public Pair(T key1, S key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    @Override
    public boolean equals(Object o) { // !!
        if(o == this) {
            return true;
        }

        if(!(o instanceof Pair other)) {
            return false;
        }

        return key1.equals(other.key1) && key2.equals(other.key2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key1, key2);
    }
}

// este pudo haber sido un static inner class, que tiene mismo comportamiento
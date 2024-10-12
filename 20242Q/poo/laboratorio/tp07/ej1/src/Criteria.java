
@FunctionalInterface
public interface Criteria<T> {

    boolean satisfies(T obj);
}

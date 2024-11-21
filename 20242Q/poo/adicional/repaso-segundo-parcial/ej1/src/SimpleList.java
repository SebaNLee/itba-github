import java.util.*;

public interface SimpleList<T> extends List<T> {

    // !! la K es local al método
    <K> Map<K, T> toMap(Function<T, K> function);
}

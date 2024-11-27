import java.util.*;

public class DoubleKeyHashMap<K1, K2, V> implements DoubleKeyMap<K1, K2, V> {

    private Map<Pair<K1, K2>, V> collection = new HashMap<>();

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public boolean containsKey(K1 firstKey, K2 secondKey) {
        return collection.containsKey(new Pair<K1, K2>(firstKey, secondKey));
    }

    public boolean containsValue(V value) {
        return collection.containsValue(value);
    }

    public V get(K1 firstKey, K2 secondKey) {
        return collection.get(new Pair<K1, K2>(firstKey, secondKey));
    }

    public void put(K1 firstKey, K2 secondKey, V value) {
        collection.put(new Pair<K1, K2>(firstKey, secondKey), value);
        return;
    }
}

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class BagImpl<T> implements Bag<T> {

    private Map<T, Integer> collection = new HashMap<>();

    @Override
    public void add(T elem) {
        int temp = collection.getOrDefault(elem, 0);
        collection.put(elem, temp + 1);
    }

    @Override
    public void remove(T elem) {

        if (!collection.containsKey(elem)) {
            throw new NoSuchElementException();
        }

        int count = collection.get(elem);

        if (count == 1) {
            collection.remove(elem);
        } else {
            collection.put(elem, count - 1);
        }
    }

    @Override
    public int count(T elem) {
        return collection.getOrDefault(elem, 0);
    }

    @Override
    public int size() {

        int total = 0;

        for (int i : collection.values()) {
            total += i;
        }

        return total;
    }

    @Override
    public int sizeDistinct() {
        return collection.size();
    }

    @Override
    public boolean contains(T elem) {
        return collection.containsKey(elem);
    }
}

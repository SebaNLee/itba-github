import java.util.*;

public class SimpleArrayList<T> extends ArrayList<T> implements SimpleList<T> {

    public <K> Map<K, T> toMap(Function<T, K> function) {

        Map<K, T> toReturn = new HashMap<>();

        for(T elem : this) {
            toReturn.put(function.apply(elem), elem);
        }

        return toReturn;
    }

    
}

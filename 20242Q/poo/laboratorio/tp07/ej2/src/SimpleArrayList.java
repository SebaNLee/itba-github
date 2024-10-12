import java.util.ArrayList;

public class SimpleArrayList<T> extends ArrayList<T> implements SimpleList<T> {

    public <E> E reduce(E initialValue, Reducer<T, E> reducer) {
        
        E toReturn = initialValue;

        for(T element : this) {
            toReturn = reducer.reduce(toReturn, element);
        }
        
        return toReturn;
    }
}

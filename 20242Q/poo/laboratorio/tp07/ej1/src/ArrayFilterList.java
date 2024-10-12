import java.util.ArrayList;

public class ArrayFilterList<T> extends ArrayList<T> implements FilterList<T> { // !! primero extends, luego implements

    public FilterList<T> filter(Criteria<T> criteria) {
        FilterList<T> toReturn = new ArrayFilterList<>();

        for(T elem : this) { // !! this
            if(criteria.satisfies(elem)) {
                toReturn.add(elem);
            }
        }

        return toReturn;
    }
}

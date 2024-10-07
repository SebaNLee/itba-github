import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TimeSetImpl<T> implements TimeSet<T> {

    private Map<TimeData, T> collection = new TreeMap<>();

    public void add(T elem, int hours, int minutes) {

        if(hours < 0 || hours > 23 || minutes < 0 || minutes >59) {
            throw new IllegalArgumentException("Horario no válido");
        }
        
        TimeData key = new TimeData(hours, minutes);
        
        collection.put(key, elem);

        return;
    }

    public void remove(T elem) { // ?? equals() ??
        // pincha acá
    }

    public int size() {

    }

    public boolean contains(T elem) {

    }

    public Set<T> retrieve(int hoursFrom, int minutesFrom, int hoursTo, int minutesTo) {

    }
}

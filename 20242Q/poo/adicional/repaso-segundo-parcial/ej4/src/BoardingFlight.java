import java.util.*;

public abstract class BoardingFlight {

    private Set<Passenger> collection;

    public BoardingFlight(Comparator<Passenger> comparator) {
        this.collection = new TreeSet<>(comparator);
    }

    public void addForBoarding(String name, int row, PassengerCategory category) {
        collection.add(new Passenger(name, row, category));
        return;
    }

    public Iterator<String> boardingCallIterator() {
        return collection.iterator();
    }
}

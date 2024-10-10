import java.util.*;

public class CitizenBook {

    private Map<String, Set<Citizen>> citizens = new HashMap<>();

    // constructor default

    public void add(Citizen citizen) {
        
        citizens.putIfAbsent(citizen.getLastName(), new TreeSet<>());
        citizens.get(citizen.getLastName()).add(citizen);

    }

    public Collection<Citizen> getByLastName(String lastName) {
        return citizens.getOrDefault(lastName, new TreeSet<>());
    }
}

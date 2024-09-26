package ar.edu.itba.poo.soltp6.ej6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonCollectionImpl implements PersonCollection {

    private Map<String, Map<String, Person>> peopleMap = new HashMap<>();

    @Override
    public void addPerson(Person person) {
        if(!peopleMap.containsKey(person.getLastName())) {
            peopleMap.put(person.getLastName(), new HashMap<>());
        }
        peopleMap.get(person.getLastName()).put(person.getFirstName(), person);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        if(!peopleMap.containsKey(lastName)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(peopleMap.get(lastName).values());
    }

    @Override
    public Person findByName(String firstName, String lastName) {
        if(!peopleMap.containsKey(lastName)) {
            return null;
        }
        return peopleMap.get(lastName).get(firstName);
    }

}

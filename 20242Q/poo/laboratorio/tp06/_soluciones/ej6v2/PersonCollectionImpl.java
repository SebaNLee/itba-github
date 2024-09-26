package ar.edu.itba.poo.soltp6.ej6v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonCollectionImpl implements PersonCollection {

    private final Map<String, Map<String, Person>> peopleMap = new HashMap<>();

    @Override
    public void addPerson(Person person) {
        peopleMap.putIfAbsent(person.getLastName(), new HashMap<>());
        peopleMap.get(person.getLastName()).put(person.getFirstName(), person);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return new ArrayList<>(peopleMap.getOrDefault(lastName, new HashMap<>()).values());
    }

    @Override
    public Person findByName(String firstName, String lastName) {
        return peopleMap.getOrDefault(lastName, new HashMap<>()).get(firstName);
    }

}

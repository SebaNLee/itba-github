import java.util.*;

public class PersonCollectionImpl implements PersonCollection {

    // apellido 1ra llave, nombre 2da llave
    Map<String, Map<String, Person>> collection = new HashMap<>();

    @Override
    public void addPerson(Person aPerson) {
        if(!collection.containsKey(aPerson.getLastName())) { // !! si existía un apellido guardado, entonces creo un nuevo hashtree del apellido
            collection.put(aPerson.getLastName(), new HashMap<>());
        }

        // ahora sé que existe un mapa asociado al apellido, entonces pido el mapa y agrego o modifico la persona
        collection.get(aPerson.getLastName()).put(aPerson.getFirstName(), aPerson);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        if(!collection.containsKey(lastName)) { // si no existe map de apellido, devuelvo ArrayList vacía
            return new ArrayList<>();
        }

        return new ArrayList<>(collection.get(lastName).values()); // !!
    }

    @Override
    public Person findByName(String firstName, String lastName) {
        if(!collection.containsKey(lastName)) {
            return null;
        }
        return collection.get(lastName).get(firstName);
    }
}


// observar que se quiere consultar todos los apellidos y nombre, entonces conviene usar mapa de mapas

// se podría simplificar con métodos de Map, solo usé métodos de HashMap de la documentación
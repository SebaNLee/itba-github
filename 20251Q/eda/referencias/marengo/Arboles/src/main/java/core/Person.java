package core;

public class Person implements Comparable<Person> {
    private Integer id;
    private String name;

    public Person(String name, Integer id) {
        this.id=id;
        this.name=name;
    }

    @Override
    public int compareTo(Person o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return name+": "+id;
    }
}

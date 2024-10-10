import java.util.Objects;

public class Citizen {

    private int dni;
    private String nombre;
    private String lastName;

    public Citizen(int dni, String nombre, String lastName) {
        this.dni = dni;
        this.nombre = nombre;
        this.lastName = lastName;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Citizen cit)) {
            return false;
        }

        return cit.dni == this.dni;
    }
}

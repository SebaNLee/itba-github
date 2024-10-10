public class Citizen {

    private int dni;
    private String nombre;
    private String LastName;

    public Citizen(int dni, String nombre, String LastName) {
        this.dni = dni;
        this.nombre = nombre;
        this.LastName = LastName;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLastName() {
        return LastName;
    }
}

import java.sql.Date;

public class Affiliate {
    
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private int number;
    private static int INITIAL_NUMBER = 1000;
    private static int CURRENT_NUMBER = INITIAL_NUMBER;

    Affiliate(String nombre, String apellido, Date fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;

        this.number = getAndIncrement();

    }
    
    private int getAndIncrement() {
        return CURRENT_NUMBER++;
    }

    // función para testear
    public void printAll() {
        System.out.println(this.nombre);
        System.out.println(this.apellido);
        System.out.println(this.fechaNacimiento);
        System.out.println(this.number);
    }
}

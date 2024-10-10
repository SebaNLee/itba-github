import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramCitizen {

    public static void main(String[] args) {
        Citizen uno = new Citizen( 111, "Uno", "One");
        Citizen dos = new Citizen( 222, "Dos", "Two");
    
        // por DNI (es único)
        Map<Integer, Citizen> byDniMap = new HashMap<>();

        byDniMap.put(uno.getDni(), uno);
        byDniMap.put(dos.getDni(), dos);

        System.out.println(byDniMap);
    
        // por nombre, pueden haber repetidos, entonces se usa List
        Map<String, List<Citizen>> byNameMap = new HashMap<>();


        System.out.println(byNameMap);
    
        // por nombre y apellido, ídem
        Map<String, Map<String, List<Citizen>>> byNameAndLastNameMap = new HashMap<>();



        System.out.println(byNameAndLastNameMap);
    
        // si quiero guardarlo en orden por DNI


        // inc
    }

}

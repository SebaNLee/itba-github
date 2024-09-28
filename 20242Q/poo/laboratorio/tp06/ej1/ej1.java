
import java.util.ArrayList;
import java.util.List;

public class ej1 {

    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList(); // lanza warning, guarda Object
        List<Integer> list2 = new ArrayList<Integer>(); // ok, se podría usar operador diamante
        List<Number> list3 = new ArrayList<Integer>(); // NO, pues por más que sea subclase no es aceptado
        List list4 = new ArrayList<Integer>(); // warning
        List<? extends Number> list5 = new ArrayList<? extends Number>(); // no compila, solo se puede usar ? para tipo de variable, no instancia
        List<?> list6 = new ArrayList(); // compila, pero no tiene sentido
    }

}

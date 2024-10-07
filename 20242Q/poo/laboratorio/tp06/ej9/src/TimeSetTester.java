

public class TimeSetTester {

    public static void main(String[] args) {
        TimeSet<String> timeSet = new TimeSetImpl<>();
        timeSet.add("Taller POO", 17, 0);
        timeSet.add("Taller PI", 13, 0);
        timeSet.add("Clase POD", 18, 30);
        System.out.println(timeSet.size());
        System.out.println(timeSet.contains("Taller POO"));
        System.out.println(timeSet.retrieve(12, 0, 17, 0));
        timeSet.remove("Clase POD");
        System.out.println(timeSet.size());
        System.out.println(timeSet.retrieve(17, 30, 18, 30));
    }

}

/* salida esperada:
    3
    true
    [Taller PI, Taller POO]
    2
    []
*/

// las soluciones usa un TreeSet, pero me pareció mejor, y a GPT, un TreeMap que use el horario como llave
// update: mal, porque es el horario que se actualiza, no el objeto

// salteo, me di cuenta del error, pero no lo soluciono

public class Ej7 {

    public static void main(String[] args) {
        try {
            System.out.println("Dentro del bloque try");
        } catch (Exception2 e) {
            System.out.println("Dentro del bloque catch");
        }
    }

}

// !!! no, pues es Checked y el compilador sabe que println no puede tirar tal excepción a chequear
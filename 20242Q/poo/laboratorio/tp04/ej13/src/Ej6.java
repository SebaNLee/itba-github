
public class Ej6 {

    public static void main(String[] args) {
        try {
            System.out.println("Dentro del bloque try");
        } catch (Exception4 e) {
            System.out.println("Dentro del bloque catch");
        }
    }

}

// !! ok, pues es Unchecked, puede suceder de la nada y por eso compila
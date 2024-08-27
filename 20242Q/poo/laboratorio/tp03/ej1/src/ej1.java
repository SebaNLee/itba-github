
public class ej1 {
    public static void main(String[] args) {
        String s1, s2;
        s1 = new String("hola");
        s2 = new String("hola");
        if (s1 == s2) {
            System.out.println("Son iguales");
        } else {
            System.out.println("Son distintos");
        }

        // a)
        String s1a, s2a;
        s1a = "hola";
        s2a = "hola";
        // funciona pues apuntan a la misma instancia, String es inmutable en Java

        if (s1a == s2a) {
            System.out.println("Son iguales");
        } else {
            System.out.println("Son distintos");
        }


        // b)
        String s1b, s2b;
        s1b = new String("hola");
        s2b = new String("hola");

        if (s1b.equals(s2b)) {
            System.out.println("Son iguales");
        } else {
            System.out.println("Son distintos");
        }
    }
}
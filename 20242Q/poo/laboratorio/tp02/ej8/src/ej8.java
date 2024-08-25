
public class ej8 {
    public static void main(String[] args) {
        Double valores[] = {1.1, 2.2, 3.3};
        for(Double valor : valores) {
            valor *= 2;
            System.out.printf("El valor es %g\n", valor);
        }
        for(Double valor : valores) {
            valor *= 2;
            System.out.printf("El valor es %g\n", valor);
        }
    }
}

// imprime lo mismo 2 veces
// imprime el doble de los valores guardados
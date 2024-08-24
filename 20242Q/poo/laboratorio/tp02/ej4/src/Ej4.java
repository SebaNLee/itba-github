

public class Ej4 {
    public static void main(String[] args) {
        Integer valor1 = Integer.valueOf(args[0]);
        Integer valor2 = Integer.valueOf(args[1]);
        Integer suma = valor1 + valor2;
        System.out.println(suma.toString());
    }
}

// toma 2 números por args y devuelve la suma usando wrapper class Integer

// a) clase, pues lo llama desde Integer.
// b) instancia, pues necesita el qué convertir a String
// c) por polimorfimo (overloading), se lo hace con la clase raiz Object

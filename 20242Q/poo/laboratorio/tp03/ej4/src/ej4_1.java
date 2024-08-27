public class ej4_1 {
    public static void main(String[] args) {
        ClassA a = new ClassB();
        a.method(3);
    }
}

// ??
// se ejecuta el método de classA, si bien recibe Number
// pero en ejecución recibe 3, que es Integer, entonces lo resuelve ahí como Integer
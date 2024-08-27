public class ej4_5 {
    public static void main(String[] args) {
        ClassA a = new ClassA();
        ClassB b = (ClassB)a;
        b.method(3);
    }
}

// !! no se puede castear una clase a una de sus subclases
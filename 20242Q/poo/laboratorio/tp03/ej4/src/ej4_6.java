public class ej4_6 {
    public static void main(String[] args) {
        ClassB b = new ClassB();
        ClassA a = (ClassA)b;
        a.method(3);
    }
}

// se puede castear una subclase a su clase padre

// se resuelven los conflictos en ClassA
public class ClassTester {
 
    public static void main(String args[]) {
        A instance;
        instance = new A();
        System.out.println(instance.m1());
        System.out.println(instance.m2());
        System.out.println(instance.m3());
        instance = new B();
        System.out.println(instance.m1());
        System.out.println(instance.m2());
        System.out.println(instance.m3());
        instance = new C();
        System.out.println(instance.m1());
        System.out.println(instance.m2());
        // comento por bucle infinito
        // System.out.println(instance.m3());
    }
 
}
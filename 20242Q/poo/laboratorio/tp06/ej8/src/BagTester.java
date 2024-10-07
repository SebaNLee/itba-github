
public class BagTester {

    public static void main(String[] args) {
        Bag<String> bag = new BagImpl<>();
        bag.add("Hola");
        bag.add("Hola");
        System.out.println(bag.count("Hola"));     // 2
        bag.add("Chau");
        System.out.println(bag.size());            // 3
        System.out.println(bag.sizeDistinct());    // 2
        System.out.println(bag.contains("Chau"));  // true
        bag.remove("Hola");
        System.out.println(bag.count("Hola"));     // 1
        bag.remove("Hola");
        System.out.println(bag.contains("Hola"));  // false
        bag.remove("Hola");                        // NoSuchElementException
    }

}


// se ve que es un Hash, pues no tiene orden
// va a ser un HashMap, usando key el elemento y un Integer (el wrapper, privimito no funciona) la cantidad
// luego, va a tener un contador global


public class Abc {
    public static void main(String[] args) {
        String letters = "ABC";
        char[] numbers = { '1', '2', '3' };
        System.out.println(letters + " easy as " + numbers);
    }
}

// los arrays no tienen un toString, entonces imprime su hashcode
// se arregla con un String.valueOf()
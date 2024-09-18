
public class ReversedIteratorTester {

    public static void main(String[] args) {
        String[] words = new String[]{"hola", "que", "tal"};
        ReversedIterator<String> arrayReversedIterator = new ReversedIterator<>(words);
        while(arrayReversedIterator.hasNext()) {
            System.out.println(arrayReversedIterator.next());
        }
        try {
            arrayReversedIterator.next();
        } catch (Exception ex) {
            System.out.println(ex.getClass());
        }
    }

}

// tiene que ser implementación genérica, este es un uso particular con Strings

/* salida esperada:

    tal
    que
    hola
    class java.util.NoSuchElementException

 */
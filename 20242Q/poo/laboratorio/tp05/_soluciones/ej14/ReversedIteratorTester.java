package ar.edu.itba.poo.soltp5.ej14;

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


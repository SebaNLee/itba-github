import java.util.*;

public class Ranker {

    private List<Set<Book>> collection = new ArrayList<>();

    Ranker() {

        for(int i = 0; i < Genre.values().length; i++) {
            collection.add(new TreeSet<>());
        }
        
    }

    public Ranker add(Genre genre, Book book) {

        book.setGenre(genre);

        collection.get(genre.ordinal()).add(book);

        return this;
    }

    public void rateUp(Book book) {

        // problema: si el ranking se modifica, entonces TreeSet causa problemas
        // solución: elimino el Book del TreeSet, modifico el rank, y lo vuelvo a meter

        // !! si el libro ya está guardado, en su respectivo género
        if(collection.get(book.getGenre().ordinal()).contains(book)) {
            collection.get(book.getGenre().ordinal()).remove(book);
        }

        book.rankUp();

        collection.get(book.getGenre().ordinal()).add(book);
    }

    public void printRanking(Genre genre) {
        Book[] temp = collection.get(genre.ordinal()).toArray(new Book[0]); // con GPT

        for(Book tempBook : temp) {
            System.out.println("%s : %d".formatted(tempBook, tempBook.getRank()));
        }        
    }

    public void printRanking() {

    }
}

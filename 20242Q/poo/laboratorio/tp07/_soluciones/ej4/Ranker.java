package ar.edu.itba.poo.soltp7.ej4;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Ranker {

    private final Set<RankedBook> rankedBooks = new TreeSet<>();

    public Ranker add(Genre genre, Book book) {
        rankedBooks.add(new RankedBook(book, genre));
        return this;
    }

    public void rateUp(Book book) {
        boolean found = false;
        /**
         * Busco el libro de la colección para removerlo,
         * aumentar en uno el ranking y volverlo a insertar.
         */
        Iterator<RankedBook> iterator = rankedBooks.iterator();
        while (iterator.hasNext() && !found) {
            RankedBook rankedBook = iterator.next();
            if(rankedBook.book.equals(book)) {
                iterator.remove();
                rankedBooks.add(new RankedBook(rankedBook));
                found = true;
            }
        }
        if(!found) {
            rankedBooks.add(new RankedBook(book));
        }
    }

    public void printRanking() {
        System.out.println("General Ranking");
        for(RankedBook rankedBook : rankedBooks) {
            System.out.println(rankedBook);
        }
    }

    public void printRanking(Genre genre) {
        System.out.println("Ranking of " + genre);
        for(RankedBook rankedBook : rankedBooks) {
            if(genre.equals(rankedBook.genre)) {
                System.out.println(rankedBook);
            }
        }
    }

}

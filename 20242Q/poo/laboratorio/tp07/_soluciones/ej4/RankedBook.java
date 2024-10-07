package ar.edu.itba.poo.soltp7.ej4;

import java.util.Objects;

public class RankedBook implements Comparable<RankedBook> {

    Book book;
    Genre genre;
    private int ranking;

    RankedBook(Book book, Genre genre) {
        this.book = book;
        this.genre = genre;
    }

    RankedBook(Book book) {
        this.book = book;
        this.ranking = 1;
    }

    RankedBook(RankedBook rankedBook) {
        this.book = rankedBook.book;
        this.ranking = rankedBook.ranking + 1;
        this.genre = rankedBook.genre;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RankedBook that &&
                book.equals(that.book) &&
                genre == that.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, genre);
    }

    @Override
    public String toString() {
        return book + " : " + ranking;
    }

    @Override
    public int compareTo(RankedBook o) {
        int rankingCompare = o.ranking - ranking;
        if(rankingCompare == 0) {
            return book.compareTo(o.book);
        }
        return rankingCompare;
    }

}

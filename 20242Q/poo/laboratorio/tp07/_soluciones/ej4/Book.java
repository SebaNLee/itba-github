package ar.edu.itba.poo.soltp7.ej4;

import java.util.Objects;

public class Book implements Comparable<Book> {

    private final String title, author;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Book book &&
                title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int compareTo(Book o) {
        int titleCmp = title.compareTo(o.title);
        if(titleCmp != 0) {
            return titleCmp;
        }
        return author.compareTo(o.author);
    }

}

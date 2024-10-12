public class Book implements Comparable<Book> {

    private String title;
    private String author;
    private Genre genre;
    private int rank;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.genre = Genre.GENERAL;
        rank = 0;
    }

    public void setGenre(Genre genre) {
        
        this.genre = genre;
        
    }

    public Genre getGenre() {
        return genre;
    }

    public void rankUp() {
        rank += 1;
    }

    public int getRank() {
        return rank;
    }

    // comparo solo por ranking
    @Override
    public int compareTo(Book other) {
        return this.rank - other.rank;
    }

    @Override
    public String toString() {
        return title;
    }
}

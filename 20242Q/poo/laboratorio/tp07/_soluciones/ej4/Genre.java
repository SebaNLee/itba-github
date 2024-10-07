package ar.edu.itba.poo.soltp7.ej4;

public enum Genre {

    FANTASY("Fantasy"),
    FICTION("Crime & Fiction"),
    DRAMA("Drama"),
    CHILDREN("Children");

    private final String name;

    Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

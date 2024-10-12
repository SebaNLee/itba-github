public enum Genre {

    GENERAL("General"), // este lo uso como default
    FANTASY("Fantasy"),
    FICTION("Fiction"),
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

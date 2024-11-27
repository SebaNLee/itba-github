public class Passenger {

    private String name;
    private int row;
    private PassengerCategory category;

    public Passenger(String name, int row, PassengerCategory category) {
        this.name = name;
        this.row = row;
        this.category = category;
    }

    public int getRow() {
        return row;
    }

    public int getCategoryNum() {
        return category.ordinal();
    }

    public String toString() {
        return name;
    }
}

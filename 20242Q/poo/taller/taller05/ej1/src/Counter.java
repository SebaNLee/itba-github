public class Counter implements Comparable<Counter> {

    private int number;
    private String airline;
    private boolean checkin;

    public Counter(int number, String airline) {
        this.number = number;
        this.airline = airline;
    }


    public void checkIn() {
        checkin = true;
    }

    @Override
    public int compareTo(Counter o) {
        int cmp = airline.compareTo(o.airline);

        if(cmp == 0) {
            cmp = Integer.compare(number, o.number); // se usa método del Wrapper, en vez de una resta, es buena práctica
        }

        return cmp;
    }
    
    @Override
    public String toString() {
        return "Counter %d for %s is ".formatted(number, airline, checkin ? "checking in" : "idle");
    }

    public boolean isCheckin() {
        return checkin;
    }

    public String getAirline() {
        return airline;
    }
}

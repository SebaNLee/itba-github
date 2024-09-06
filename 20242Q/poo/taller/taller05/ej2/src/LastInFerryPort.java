import java.time.LocalTime;

public class LastInFerryPort extends TimeFerryPort {

    private Ferry lastFerry;


    public LastInFerryPort(String name, FerryCompany company, int docks, LocalTime open, LocalTime close) {
        super(name, company, docks, open, close);
    }

    @Override
    public void dock(Ferry ferry, LocalTime time) {
        super.dock(ferry, time);
        lastFerry = ferry
    }
}

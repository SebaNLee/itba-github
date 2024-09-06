import java.time.LocalTime;

public class TimeFerryPort extends FerryPort {

    private LocalTime open;
    private LocalTime close;

    public TimeFerryPort(String name, FerryCompany company, int docks, LocalTime open, LocalTime close) {
        super(name, company, docks);
        this.open = open;
        this.close = close;

    }

    @Override
    protected boolean canDock(Ferry ferry, LocalTime time) {
        return super.canDock(ferry, time) && time.isAfter(open) && time.isBefore(close);
    }
}

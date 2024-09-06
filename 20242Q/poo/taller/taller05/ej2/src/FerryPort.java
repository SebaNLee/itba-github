import java.time.LocalTime;

public class FerryPort {

    private String name;
    private FerryCompany company;
    private int docks;
    private int occupiedDocks;

    public FerryPort(String name, FerryCompany company, int docks) {
        this.name = name;
        this.company = company;
        this.docks = docks;
    }

    private boolean canDock(Ferry ferry, LocalTime time) {
        return occupiedDocks < docks && company.equals(ferry.getCompany());
    }

    public void dock(Ferry ferry, LocalTime time) {
        if(canDock(ferry, time)) {
            occupiedDocks++;
            System.out.println("Ferry %s docked at %s".formatted(ferry.getName(), name));
        }
    }

    public void undock(Ferry ferry, LocalTime time) {
        if(occupiedDocks == 0) {
            throw new RuntimeException();
        }

        occupiedDocks--;
        System.out.println("Ferry is %s is undocked");
    }
}

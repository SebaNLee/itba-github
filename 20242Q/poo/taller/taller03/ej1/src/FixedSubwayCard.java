public class FixedSubwayCard extends SubwayCard {

    private int rides;

    FixedSubwayCard(SubwayCentral sc, int rides) {
        super(sc);
        this.rides = rides;
    }

    @Override
    protected boolean canRide() {
        if(rides > 0) {
            return true;
        } 
        else {
            return false;
        }
    }

    @Override
    public void ride() {
        if(canRide() == true) {
            rides--;

            return;
        }
        else {
            System.err.println("Sin viajes!");

            return;
        }
    }
}

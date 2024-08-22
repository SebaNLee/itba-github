public class RechargeableSubwayCard extends SubwayCard {

    private double balance;

    RechargeableSubwayCard(SubwayCentral sc) {
        super(sc);
    }

    public void recharge(double amount) {
        balance += amount;
    }

    protected double rideCost() {
        return getSubwayCentral().getRideCost();
    }

    @Override
    protected boolean canRide() {
        if (balance > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void ride() {
        if (canRide() == true) {
            balance -= getSubwayCentral().getRideCost();

            return;
        } else {
            System.err.println("Sin viajes!");

            return;
        }
    }
}

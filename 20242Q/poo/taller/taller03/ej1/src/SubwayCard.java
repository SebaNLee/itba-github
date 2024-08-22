public abstract class SubwayCard {

    private SubwayCentral subwayCentral;

    SubwayCard(SubwayCentral sc) {
        this.subwayCentral = sc;
    }

    protected abstract boolean canRide();

    protected SubwayCentral getSubwayCentral() {
        return subwayCentral;
    }

    public abstract void ride();
}

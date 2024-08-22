public class DiscountRechargeableSubwayCard extends RechargeableSubwayCard {
    double discountPercentage;

    DiscountRechargeableSubwayCard(SubwayCentral sc, double dp) {
        super(sc);
        discountPercentage = dp;
    }

    @Override
    protected double rideCost() {
        return super.rideCost() * this.discountPercentage;
    }
}

public class Gear {
    private int chainring;
    private double cog;


    public Gear(int chainring, double cog) {
        this.chainring = chainring;
        this.cog = cog;
    }

    public double ratio() {
        return chainring / cog;
    }
}

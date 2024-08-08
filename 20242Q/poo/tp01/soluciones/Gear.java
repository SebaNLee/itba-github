package ar.edu.itba.poo.soltp1.ej1;

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

    public static void main(String[] args) {
        Gear firstGear = new Gear(52, 11);
        System.out.println(firstGear.ratio()); // 4.7272727272727275

        Gear secondGear = new Gear(30, 27);
        System.out.println(secondGear.ratio()); // 1.1111111111111112
    }

}

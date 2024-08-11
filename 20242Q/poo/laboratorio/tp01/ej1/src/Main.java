

public class Main {
    public static void main(String[] args) {
        Gear gear1 = new Gear(52, 11);
        System.out.println(gear1.ratio()); // 4.7272727272727275

        Gear gear2 = new Gear(30, 27);
        System.out.println(gear2.ratio()); // 1.1111111111111112
    }
}

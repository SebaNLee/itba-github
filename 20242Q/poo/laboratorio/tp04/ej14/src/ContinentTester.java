

public class ContinentTester {

    public static void main(String[] args) {
        System.out.println("Densidades de población:");
        for(Continent continent : Continent.values()) { // !! uso values, static
            System.out.println("%s = %.2f".formatted(continent, continent.getPopulationRatio()));
        }
        System.out.printf("%.2f", Continent.valueOf("AMERICA").getPopulationRatio()); // uso valueOf()
    }

}

/* salida deseada:

    Densidades de población:
    África = 36.67
    América = 23.57
    Asia = 102.33
    Europa = 73.00
    Oceanía = 4.33
    23,57

 */
package ar.edu.itba.poo.tp1.ej1;

public class RatioScript {

    public static void main(String[] args) {
        int chainring = 52; // Número de dientes
        double cog = 11;
        double ratio = chainring / cog;
        System.out.println(ratio); // 4.7272727272727275

        chainring = 30;
        cog = 27;
        ratio = chainring / cog;
        System.out.println(ratio); // 1.1111111111111112
    }

}

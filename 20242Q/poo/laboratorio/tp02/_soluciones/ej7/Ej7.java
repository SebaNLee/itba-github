package ar.edu.itba.poo.soltp2.ej7;

public class Ej7 {

    public static void main(String[] args) {
        int[] frec = new int[10];
        int max = 0;
        for (String s : args) {
            int i = Integer.valueOf(s) / 10;
            if (i < 0 || i >= 10) {
                System.out.println("Valor incorrecto: " + s);
                return;
            }
            frec[i]++;
            if (frec[i] > max) {
                max = frec[i];
            }
        }
        imprimirHist(frec, max);
    }

    private static void imprimirHist(int[] frec, int max) {
        for (int i = max; i > 0; i--) {
            System.out.print("| ");
            for (int aFrec : frec) {
                System.out.print(aFrec >= i ? '*' : ' ');
                System.out.print(" | ");
            }
            System.out.println();
        }
        for (int aFrec : frec) {
            System.out.print("+---");
        }
        System.out.print("+\n");
        for (int i = 0; i <= frec.length; i++) {
            System.out.printf("%-4d", i*10);
        }
    }

}

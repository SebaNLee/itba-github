public class Histograma {
    public static void main(String[] args) {

        int currIndex, max = 0;
        int[] counterVector = new int[10];

        for(String curr : args) {
            currIndex = Integer.valueOf(curr) / 10;

            counterVector[currIndex]++;

            if(counterVector[currIndex] > max) {
                max = counterVector[currIndex];
            }
        }

        printHistograma(counterVector, max);

    }


    public static void printHistograma(int[] counterVector, int max) {
        for(int i = 0; i < max; i++) {

            System.out.print("|");

            for(int j = 0; j < 10; j++) {
                
                if(counterVector[j] >= max - i) {
                    System.out.print("*  |");
                }
                else {
                    System.out.print("   |");
                }
            }
            System.out.println();
        }


        // mala práctica, pero lo hago porque la consiga dice de 0 a 100
        System.out.println("+---+---+---+---+---+---+---+---+---+---+");
        System.out.println("0   10  20  30  40  50  60  70  80  90  100");
    }
}

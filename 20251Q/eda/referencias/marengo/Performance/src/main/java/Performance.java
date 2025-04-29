public class Performance {

    public static final int N = 100000000;

    public static void main(String[] args) {
        // generate array
        int[] myArray = new int[N];

        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;

        // performance testing
        Timer t1 = new Timer();
        int rta1 = AlgoA.max(myArray);
        t1.stop();

        Timer t2 = new Timer();
        int rta2 = AlgoB.max(myArray);
        t2.stop();

        System.out.printf("max Algo A %d. Delay %d (ms)%n", rta1, t1.getElapsedTime());
        System.out.printf("max Algo B %d. Delay %d (ms)%n", rta2, t2.getElapsedTime());
    }

}
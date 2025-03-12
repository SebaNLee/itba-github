package itba;

public class Tester {
    public static void main(String[] args) {
        TimerJoda timer = new TimerJoda(100);

        timer.restart();

        for(int i = 0; i < 100000; i++) {
            System.out.print("");
        }

        timer.stop();

        System.out.println(timer);
    }
}

package eda;

public class Tester {

    public static void main(String[] args) {
        MyTimerV2 timer = new MyTimerV2();

        
        // bla bla bla
        for(int i = 0; i < 10000; i++) {
            System.err.print("");
        }


        timer.end(System.currentTimeMillis());

        System.err.println(timer);
    }
}

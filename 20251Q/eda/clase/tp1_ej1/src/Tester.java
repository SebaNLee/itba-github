package eda.clase.tp1_ej1.src;

public class Tester {

    public static void main(String[] args) {
        MyTimer timer = new MyTimer();
        
        // bla bla bla
        for(int i = 0; i < 10000; i++) {
            System.err.print("");
        }


        timer.end();

        System.err.println(timer);
    }
}

package main.java.ar.edu.itba.pod.concurrency.exercises.e3;

public class RandomThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000); // simulate compute
            System.out.println(Math.random());
        } catch (InterruptedException e) {
            System.out.println("Eh dale! Me interrumpieron!");
            return;
        }
    }
}

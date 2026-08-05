package ar.edu.itba.pod.concurrency.exercises.e1;

public class HelloThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from thread");
    }

    public static void main(String[] args) {
        Thread thread = new HelloThread();
        thread.start();
    }
}

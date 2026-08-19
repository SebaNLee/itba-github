package main.java.ar.edu.itba.pod.concurrency.exercises.e3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RandomThread());

        thread.start();

        thread.join();

        System.out.println("Exit main thread");
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="main.java.ar.edu.itba.pod.concurrency.exercises.e3.Main"
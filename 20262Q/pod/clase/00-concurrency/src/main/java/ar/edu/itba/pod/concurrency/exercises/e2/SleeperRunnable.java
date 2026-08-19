package main.java.ar.edu.itba.pod.concurrency.exercises.e2;

public class SleeperRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("Siesta numero: " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }
        }
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="main.java.ar.edu.itba.pod.concurrency.exercises.e2.SleeperRunnable"
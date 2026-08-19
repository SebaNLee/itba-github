package ar.edu.itba.pod.concurrency.threads.e2;

public class ThreadStateViewer {
    public static void main(String[] args) throws InterruptedException {
        String lock = "lock";

        Thread thread = new Thread(() -> {
            System.out.printf("Hello!, my state is %s%n", Thread.currentThread().getState());
            try {
                Thread.sleep(2000);
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.printf("Thread state: %s%n", thread.getState());
        thread.start();

        Thread.sleep(500);
        System.out.printf("Thread state: %s%n", thread.getState());

        Thread.sleep(2000);
        System.out.printf("Thread state: %s%n", thread.getState());
        
        synchronized (lock) {
            lock.notifyAll();
        }

        thread.join();

        System.out.printf("Thread state: %s%n", thread.getState());
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="ar.edu.itba.pod.concurrency.threads.e2.ThreadStateViewer"

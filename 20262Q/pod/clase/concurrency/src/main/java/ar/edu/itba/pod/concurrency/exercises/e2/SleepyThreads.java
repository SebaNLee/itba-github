package main.java.ar.edu.itba.pod.concurrency.exercises.e2;

public class SleepyThreads {
    public static void main(String[] args) throws InterruptedException {
        final Thread[] ts = new Thread[2];
        
        for (int i = 0; i < ts.length; i++) {
            Thread thread = new Thread(new SleeperRunnable(), "sl-" + i);
            thread.start();
            ts[i] = thread;
        }

        // nota: corren los threads hasta el primer .sleep() y se quedan dormidos

        ts[1].interrupt(); // nota: ya que el thread está el .sleep(), esto lanza un InterruptedException en el thread
        ts[0].join(); // nota: el thread de main(), este de acá, se queda esperando a thread sl-0 hasta que termine

        // nota: agrego print de referencia
        System.out.println("Recién termina main thread");
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="main.java.ar.edu.itba.pod.concurrency.exercises.e2.SleepyThreads"
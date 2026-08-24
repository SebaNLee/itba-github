package ar.edu.itba.pod.e2;

public class UseStack {
    private static final int OPERATION_PER_THREAD = 1000;
    private static final int THREAD_COUNT = 500;

    private static Stack stack;

    private static final Runnable operation = () -> {
        for (int i = 0; i < OPERATION_PER_THREAD; i++) {
            stack.push(1);
            stack.pop();
        }
    };
    
    public static void main(String[] args) throws InterruptedException {
        stack = new Stack();

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(operation);
            threads[i].start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
    }
}

// observar que falla la mayoría de las veces con ArrayIndexOutsOfBoundsExceptions

// mvn compile && mvn exec:java -Dexec.mainClass="ar.edu.itba.pod.e2.UseStack"

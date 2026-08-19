package ar.edu.itba.pod.concurrency.threads.e3;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorAnalyzer.class);

    private static final int THREAD_COUNT = 4;

    private static final Function<Integer, Callable<Void>> runnerFactory = (Integer index) -> () -> {
        logger.info("Starting runner: {}", index);
        Thread.sleep(1500);
        logger.info("Ending runner: {}", index);
        return null;
    };

    public static void execute(ExecutorService pool) {
        try {
            List<Future<Void>> futures = IntStream.range(0, THREAD_COUNT)
                    .mapToObj(index -> pool.submit(runnerFactory.apply(index)))
                    .toList();

            for (Future<Void> future : futures) {
                future.get();
            }

            pool.shutdown();

            if (!pool.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }

        } catch (InterruptedException | ExecutionException e) {
            pool.shutdownNow();
        }
    }

    public static void main(String[] args) {

        logger.info("Cached Thread Pool");
        execute(Executors.newCachedThreadPool());

        logger.info("Fixed Thread Pool");
        execute(Executors.newFixedThreadPool(2));

        logger.info("Single Thread Executor");
        execute(Executors.newSingleThreadExecutor());

        logger.info("Single Thread Executor but rejecting");

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.AbortPolicy());

        execute(executor);
    }
}



// mvn compile && mvn exec:java -Dexec.mainClass="ar.edu.itba.pod.concurrency.threads.e3.ExecutorAnalyzer"

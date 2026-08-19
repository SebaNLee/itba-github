package ar.edu.itba.pod.concurrency.iii.e3;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Benchmar to compare between {@link Arrays#parallelSort(int[])} and
 * {@link Arrays#sort(int[])}
 */
public class SortBenchmark {

    @Test
    public void benchmark_all() {
        benchmark(generateArray(10000000, new Random()), Arrays::parallelSort, "Parallel sort 10M");
        benchmark(generateArray(25000000, new Random()), Arrays::parallelSort, "Parallel sort 25M");
        benchmark(generateArray(50000000, new Random()), Arrays::parallelSort, "Parallel sort 50M");
        benchmark(generateArray(10000000, new Random()), Arrays::sort, "Sort 10M");
        benchmark(generateArray(25000000, new Random()), Arrays::sort, "Sort 25M");
        benchmark(generateArray(50000000, new Random()), Arrays::sort, "Sort 50M");
    }

    private int[] generateArray(final int size, final Random random) {
        final int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000);
        }
        return array;
    }

    private void benchmark(final int[] array, final Consumer<int[]> order, final String message) {
        long accumulate = 0;
        for (int i =0; i < 4; i++) {
            final int []cp = Arrays.copyOf(array, array.length);
            final long startTime = System.currentTimeMillis();
            order.accept(cp);
            final long finishTime = System.currentTimeMillis();
            accumulate += finishTime - startTime;
        }
        System.out.println(message + " took: " + (accumulate /4) + " ms");
    }
}

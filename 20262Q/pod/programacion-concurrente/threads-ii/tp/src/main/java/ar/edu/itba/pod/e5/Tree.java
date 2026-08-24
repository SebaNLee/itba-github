package ar.edu.itba.pod.e5;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

// copiado de soluciones

public class Tree {
    private static final String PATH = Paths.get("").toAbsolutePath().toString();

    public static void main(String[] args) throws IOException, InterruptedException,
            ExecutionException {
        Path path = Paths.get(PATH);
        List<Path> files = listFiles(path);

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Callable<Long>> calls = new ArrayList<>();
        for (Path p : files) {
            calls.add(new FileLinesCounter(p));
        }
        List<Future<Long>> futures = executor.invokeAll(calls);
        Long count = 0L;
        for (Future<Long> future : futures) {
            count += future.get();
        }
        System.out.println(count);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static List<Path> listFiles(Path dir) throws IOException {
        List<Path> result = new ArrayList<>();
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        for (Path entry : stream) {
            result.add(entry);
        }
        return result;
    }

    public static class FileLinesCounter implements Callable<Long> {
        private Path path;

        public FileLinesCounter(Path path) {
            this.path = path;
        }

        @Override
        public Long call() throws IOException {
            if (path.toFile().isFile()) {
                return Files.lines(path, StandardCharsets.ISO_8859_1).count();
            }
            return 0L;
        }
    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="ar.edu.itba.pod.e5.Tree"
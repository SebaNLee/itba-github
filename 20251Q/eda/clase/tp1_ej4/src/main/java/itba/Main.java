package itba;

import org.joda.time.Instant;

public class Main {
    public static void main(String[] args) {
        Instant start = new Instant();

        for(int i = 0; i < 10000; i++) {
            System.out.print("");
        }

        Instant end = new Instant();

        System.err.println(start);
        System.err.println(end);
    }
}
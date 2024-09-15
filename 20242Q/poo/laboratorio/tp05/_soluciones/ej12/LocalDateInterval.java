package ar.edu.itba.poo.soltp5.ej12;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LocalDateInterval implements Iterable<LocalDate> {

    private final LocalDate from, to;
    private final int step;

    public LocalDateInterval(LocalDate from, LocalDate to, int step) {
        this.from = from;
        this.to = to;
        this.step = step;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new LocalDateIntervalIterator();
    }

    private class LocalDateIntervalIterator implements Iterator<LocalDate> {

        private LocalDate current = from;

        @Override
        public boolean hasNext() {
            return step > 0 && !current.isAfter(to) || step < 0 && !current.isBefore(to);
        }

        @Override
        public LocalDate next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            LocalDate aux = current;
            current = current.plusDays(step);
            return aux;
        }

    }

}

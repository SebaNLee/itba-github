import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LocalDateInterval implements Iterable<LocalDate> {

    private LocalDate from, to;
    private int interval;

    public LocalDateInterval(LocalDate from, LocalDate to, int interval) {
        this.from = from;
        this.to = to;
        this.interval = interval;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new LocalDateIntervalIterator();
    }

    // lo hago con inner class
    private class LocalDateIntervalIterator implements Iterator<LocalDate> {
        // no necesita constructor, pues usa el constructor implícito, pues no necesita instanciamiento adicional

        private LocalDate current = from;

        @Override
        public boolean hasNext() {
            return (interval > 0 && !current.isAfter(to)) || 
                    (interval < 0 && !current.isBefore(to));
        }
        
        @Override
        public LocalDate next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            LocalDate aux = current;
            current = current.plusDays(interval);
            
            return aux;
        }
    }

}

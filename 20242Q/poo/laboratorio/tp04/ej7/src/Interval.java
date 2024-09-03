import java.util.Objects;

public class Interval {

    private double start, end, increment;

    private static final String INTERVAL_SEPARATOR = ", ";
    private static final String INTERVAL_START = "[";
    private static final String INTERVAL_END = "]";

    public Interval(double start, double end, double increment) {

        if((start > end && increment > 0) || (start < end && increment < 0) || increment == 0) {
            throw new IllegalArgumentException("start, end y/o increment inválidos");
        }

        this.start = start;
        this.end = end;
        this.increment = increment;
    }

    public Interval(double start, double end) {
        this(start, end, 1);
    }

    public long size() {
        return (long) ((end - start) / increment) + 1;
    }

    public double at(long index) {
        if(index < 0) {
            throw new IllegalArgumentException("Índice inválido");
        }

        double answer = start + increment * index;

        if(checkValue(answer)) {
            throw new IllegalArgumentException("Index out of range");
        }

        return answer;
    }

    // devuelve 0 si no pertenece
    public long indexOf(double valor) {
        if (checkValue(valor)) {
            return 0;
        }
    
        // en vez de chequear todo, puedo comparar calculando su supuesto índice
        long index = (long) ((valor - start) / increment);
        if (Double.compare(at(index), valor) == 0) {
            return index;
        }
    
        return 0;
    }

    private boolean checkValue(double value) {
        return (value < start && increment > 0) || (value > end && increment > 0) || (value > start && increment < 0) || (value < end && increment < 0);
    }
    

    public boolean includes(double valor) {
        return indexOf(valor) > 0;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder(INTERVAL_START);

        boolean flagStop = true;

        for(double current = start; flagStop; current += increment) {
            answer.append(current).append(INTERVAL_SEPARATOR);
            
            if(current == end) {
                flagStop = false;
            }
        }

        answer.setLength(answer.length() - 2); // quiero sacarme el ", " de más
        answer.append(INTERVAL_END);
    
        return answer.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Interval interval &&
                Double.compare(interval.start, start) == 0 &&
                Double.compare(interval.end, end) == 0 &&
                Double.compare(interval.increment, increment) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, increment);
    }
}



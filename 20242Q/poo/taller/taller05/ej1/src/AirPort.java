import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class AirPort {

    private static final int INITIAL_DIM = 1;
    private Counter[] counters = new Counter[INITIAL_DIM]; // se puede inicializar acá o en addCounter()
    private int dim = 0;

    public AirPort() {

    }

    public void resize() {
        counters = Arrays.copyOf(counters, dim + INITIAL_DIM);
    }

    public Counter addCounter(String airline) {
        
        if(counters.length == dim) {
            resize();
        }

        Counter aux = new Counter(dim, airline);
        counters[dim++] = aux;
        return aux;
    }

    // obs: Predicate devuelve un boolean, a diferencia de Comparable y Comparator
    public void counterCheckIn(Predicate<Counter> predicate) { // !! recibe un Predite por parámetro
        for(int i = 0; i < dim; i++) {
            if(predicate.test(counters[i])) {
                counters[i].checkIn();
                break; // termino, pues solo quiero el primero
            }
        }
    }

    // !!! devuelven un array de Counters
    public Counter[] airlineOrderCounters() {
        return orderCounters(Comparator.naturalOrder());
    }

    public Counter[] airlineDescendingOrderCounters() {
        return orderCounters(Comparator.reverseOrder());
    }

    // !! repasar exhaustivamente
    private Counter[] orderCounters(Comparator<Counter> comparator) { // en este caso recibe un Comparator
        Counter[] aux = Arrays.copyOf(counters, dim);

        Arrays.sort(aux, comparator);

        return aux;
    }
}

import java.util.*;

public class MultiMapImpl<T, E extends Comparable<? super E>> implements MultiMap<T, E> { // !! me tengo que garantizar de que lo que devuelvo sea Comparable, pues necesito devolverla con orden

    private Map<T, Set<E>> multiMap = new HashMap<>();

    // constructor default

    /**
     * Agrega un par key,value al multimapa si el par no existe.
     */
    @Override
    public void put(T key, E value) {

        if(!multiMap.containsKey(key)) {
            multiMap.put(key, new TreeSet<>(Comparator.reverseOrder())); // !!
        } // más simple con putIfAbsent()

        multiMap.get(key).add(value);
    }

    /**
     * Cantidad de valores del multimapa.
     */
    @Override
    public int size() {
        
        int count = 0;

        for(T key : multiMap.keySet()) {
            count += multiMap.get(key).size();
        }

        return count;
    }

    /**
     * Cantidad de valores del multimapa para la clave key.
     */
    @Override
    public int size(T key) {
        
        if(!multiMap.containsKey(key)) {
            return 0;
        } // más simple con getOrDefault()

        return multiMap.get(key).size();
    }

    /**
     * Elimina la clave del multimapa (con todos sus valores) si existe.
     */
    @Override
    public void remove(T key) {
        
        multiMap.remove(key);
    }

    /**
     * Elimina el valor value de la clave key si existe.
     */
    @Override
    public void remove(T key, E value) {

        if(multiMap.containsKey(key)) {
            multiMap.get(key).remove(value);
        }
    }

    /**
     * Colección ordenada descendentemente de valores de clave key.
     */
    @Override
    public Iterable<E> get(T key) {

        return multiMap.get(key);
    }
}

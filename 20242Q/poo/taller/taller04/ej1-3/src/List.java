
// ej1

public interface List<T> {
    
    void add(T elem);

    // devuelve -1 si no encuentra
    int contains(T elem);

    boolean remove(int index);

    boolean isEmpty();

    // devuelve true si encuentra y elimina
    default boolean removeElement(T elem) {
        
        int index = contains(elem);

        if(index != -1) {
            remove(index);
            return true;
        }

        return false;
    }
}

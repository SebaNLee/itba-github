import java.util.Arrays;

// ej3

public class ArrayList<T> implements List<T> { // !! observar que tiene que ser el mismo T
    private T[] array;
    private int dim;

    public ArrayList() {
        array = (T[]) new Object[10]; // esto cambia a casteo
        dim = 0;
    }

    @Override
    public void add(T elem) {
        if(array.length == dim) {
            array = Arrays.copyOf(array, array.length + 1);
        }

        array[dim++] = elem;
    }

    // devuelve -1 si no encuentra
    @Override
    public int contains(T elem) {
        for(int i = 0; i < dim; i++) {
            if(array[i].equals(elem)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean remove(int index) {
        if(index >= 0 && index < dim) {
            for (int i = index; i < dim - 1; i++) {
                array[i] = array[i + 1];
            }
            array[--dim] = null; 

            return true;
        }
        else {
            return false; // error
        }
    }

    @Override
    public boolean isEmpty() {
        return dim == 0;
    }

    // no se implementaría default removeElement
}

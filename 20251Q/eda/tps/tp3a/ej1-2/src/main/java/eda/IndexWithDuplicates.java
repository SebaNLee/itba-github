package eda;

import java.lang.reflect.Array;
import java.util.Arrays;


public class IndexWithDuplicates implements IndexService {

    private static int CHUNK = 5;
    private int[] elements = new int[CHUNK];
    private int dim;


    // constructor default

    @Override
    public void initialize(int [] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(elements); // ordeno
        this.elements = Arrays.copyOf(elements, elements.length); // sobreescribo
        this.dim = elements.length;
    }

    @Override
    public boolean search(int key) {
        int result = binarySearch(key);

        if (result == -1) {
            return false;
        }

        return true;
    }
    
    @Override
    public void insert(int key) {
        // chunks
        if (dim == elements.length) {
            elements = Arrays.copyOf(elements, elements.length + CHUNK);
        }

        // posición a insertar
        int closestPosition = getClosestPosition(key);

        // copio los elementos desde arriba
        for (int i = dim - 1; i >= closestPosition; i--) {
            elements[i + 1] = elements[i];
        }

        dim++;
        elements[closestPosition] = key;
    }
    
    @Override
    public void delete(int key) {
        int index = binarySearch(key);

        // ignora si no existe el elemento
        if (index == -1) {
            return;
        }

        // actualizo dim (copia bien)
        dim--;

        // voy pisando elementos desde index
        for (int i = index; i < dim; i++) {
            elements[i] = elements[i + 1];
        }

        // checkeo chunks
        if (dim % CHUNK == 0) {
            elements = Arrays.copyOf(elements, elements.length - CHUNK);
        }
    }
    
    // lo hago naive 
    @Override
    public int occurrences(int key) {
        
        // se podría hacer con métodos firstOccurence y lastOccurence 
        
        int counter = 0;

        for (int i = 0; i < dim; i++) {
            if (key == elements[i]) {
                counter++;
            }
        }

        return counter;
    }

    @Override
	public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
        return null;
    }


    @Override
    public void sortedPrint() {
        System.out.println(Arrays.toString(Arrays.copyOf(elements, dim)));
    }
 
    @Override
    public int getMax() {
        return elements[dim - 1];
    }
         
    @Override
    public int getMin() {
        return elements[0];
    }


    // devuelve índice del elemento encontrado
    // devuelve -1 si no está
    // versión iterativa
    private int binarySearch(int target) {
        int left = 0;
        int right = dim - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (elements[mid] == target) {
                return mid;
            }

            if (elements[mid] < target) {
                left = mid + 1; // busca en la mitad derecha
            }
            else {
                right = mid - 1; // busca la mitad izquierda
            }
        }

        return -1;
    }

    // lo hago naive
    private int getClosestPosition(int key) {

        // podría usar binarySearch() o algo antes, pero recorro naive

        for (int i = 0; i < dim; i++) {
            if (key <= elements[i] ) {
                return i;
            }
        }

        return dim;
    }

    
}




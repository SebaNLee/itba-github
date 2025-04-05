package eda;

import java.util.Arrays;
//Implementar la clase IndexWithDuplicates que implementa dicha interface y acepta que las claves de búsqueda pueden aparecer repetidas. Internamente debe manejar un arreglo (garantizando contigüidad de las claves) el cual crecerá y/o decrecerá de a chunks (no de a 1 componente).
//Importante: para el método delete primero se lo busca y eso se hace en O(log2 N) aunque no se puede garantizar que el delete tenga dicha complejidad porque debe mover componentes. Para el método insert también se quiere hacer algo similar, es decir no decidir linealmente dónde debe ir el elemento. Implementar internamente el método getClosestPosition que calcula en O(log2 N) la posición donde debería insertarse la clave solicitada. Es decir, como está ordenado, no buscar linealmente dónde debe
//insertarse, sino hacerlo con complejidad logarítmica. Obviamente, no se puede garantizar que la inserción tenga finalmente dicha complejidad por el movimiento posible de las componentes para garantizar contigüidad.
public class IndexWithDuplicates implements IndexService {
        public int[] index;
        private int size;
        private static final int DIM = 5;
        IndexWithDuplicates() {
            index = new int[DIM];
            this.size = 0;
        }
        @Override
        public void initialize(int[] elements) {
            if (elements == null)
                throw new NullPointerException("El indice es nulo");
            index = new int[DIM];
            size = 0;
            for (int i = 0; i < elements.length; i++){
                insert(elements[i]);
            }
        }
        @Override
        public boolean search(int key) {
            return searkKey(key) == -1? false : true;
        }
        private int searkKey(int key){
            int l = 0;
            int r = size-1;
            while (l <= r) {
                int m = l + (r-l)/2;
                if (index[m] == key) // Check if x is present at mid
                    return m;
                if (index[m] < key) // If x greater, ignore left half
                    l = m + 1;
                else // If x is smaller, ignore right half
                    r = m - 1;
            }
            return -1; // if we reach here, then element was not present
        }

        private int getClosestPosition(int key){
            return lowerBound(index, 0, size, key);
        }

        // Devuelve el primer valor de array que cumple NO ser menor que key
        private int lowerBound(int[] array, int left, int right, int key){
            if(left >= right){
                return left;
            }
            int midPoint = (left + right) / 2;
            if(key <= array[midPoint])
                return lowerBound(array, left, midPoint, key);
            return lowerBound(array, midPoint + 1, right, key);
        }

        private int lowerBound(int key){
            return lowerBound(index, 0, size, key);
        }

        // Devuelve el primer valor de array que cumple ser mayor que key
        private int upperBound(int[] array, int left, int right, int key){
            if(left >= right){
                return left;
            }
            int midPoint = (left + right) / 2;
            if(key < array[midPoint])
                return upperBound(array, left, midPoint, key);
            return upperBound(array, midPoint + 1, right, key);
        }

        private int upperBound(int key){
            return upperBound(index, 0, size, key);
        }

        @Override
        public void insert(int key) {
            if(size == index.length){
                int[] aux = new int[size + DIM];
                System.arraycopy(index, 0, aux, 0, size);
                index = aux;
            }
            int where = getClosestPosition(key);
            System.arraycopy(index, where, index, where + 1, size - where);
            index[where] = key;
            size++;
        }
        @Override
        public void delete(int key) {
            int where = lowerBound(index, 0, size, key);
            if(index[where] == key) {
                System.arraycopy(index, where + 1, index, where, size - where - 1);
                size--;
            }
        }
        @Override
        public int occurrences(int key) {
            return upperBound(key) - lowerBound(key);
        }

        @Override
        public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
            if (leftKey > rightKey || (leftKey == rightKey && (!leftIncluded || !rightIncluded)))
                return new int[0];
            int first = leftIncluded ? lowerBound(leftKey) : upperBound(leftKey);
            int last = rightIncluded ? upperBound(rightKey) : lowerBound(rightKey);
            int[] ans = Arrays.copyOfRange(index, first, last);
            return ans;
        }

        @Override
        public void sortedPrint() {
            System.out.printf("[");
            for (int i = 0; i < size; i++){
                System.out.printf("%d, ", index[i]);
            }
            System.out.printf("]\n");
        }

        @Override
        public int getMax() {
            if (size == 0)
                throw new RuntimeException("Index is empty");
            return index[size-1];
        }

        @Override
        public int getMin() {
            if (size == 0)
                throw new RuntimeException("Index is empty");
            return index[0];
        }
}
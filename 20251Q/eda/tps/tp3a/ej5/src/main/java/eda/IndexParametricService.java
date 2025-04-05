package eda;

//Implementar la clase IndexWithDuplicates que implementa dicha interface y acepta que las claves de búsqueda pueden aparecer repetidas. Internamente debe manejar un arreglo (garantizando contigüidad de las claves) el cual crecerá y/o decrecerá de a chunks (no de a 1 componente).
//Importante: para el método delete primero se lo busca y eso se hace en O(log2 N) aunque no se puede garantizar que el delete tenga dicha complejidad porque debe mover componentes. Para el método insert también se quiere hacer algo similar, es decir no decidir linealmente dónde debe ir el elemento. Implementar internamente el método getClosestPosition que calcula en O(log2 N) la posición donde debería insertarse la clave solicitada. Es decir, como está ordenado, no buscar linealmente dónde debe
//insertarse, sino hacerlo con complejidad logarítmica. Obviamente, no se puede garantizar que la inserción tenga finalmente dicha complejidad por el movimiento posible de las componentes para garantizar contigüidad.
import java.lang.reflect.Array;
import java.util.Arrays;
public class IndexParametricService<T extends Comparable<? super T>> implements IndexParametricServiceInterface<T> {
    private T[] elements;
    static final private int CHUNK = 10;
    private int length = 0;

    @SuppressWarnings("unchecked")
    public IndexParametricService(Class<? extends T> theClass) {
        this.elements = (T[]) Array.newInstance(theClass, CHUNK);
    }

    @Override
    public void initialize(T[] elements) {
        if (elements == null)
            throw new IllegalArgumentException();
        Arrays.sort(elements);
        this.elements = elements;
        length = elements.length;
    }

    public int size() {
        return length;
    }

    @Override
    public boolean search(T key) {
        return getIdx(key) != -1;
    }

    private int getIdx(T key) {
        int result = getClosestPosition(key);
        if (result >= length || elements[result] != key) {
            return -1;
        }
        return result;
    }

    private int getClosestPosition(T key) {
        int left = 0, right = length-1, mid;
        while (left <= right) {
            mid = (left + right)/2;
            if (elements[mid].compareTo(key) == 0) {
                return mid;
            }
            if (elements[mid].compareTo(key) < 0)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return left;
    }

    @Override
    public void insert(T key) {
        if (length == elements.length) {
            elements = Arrays.copyOf(elements, length + CHUNK);
        }
        int position = getClosestPosition(key);
        length++;
        System.arraycopy(elements, position, elements, position + 1, length - position);
        elements[position] = key;
    }

    @Override
    public void delete(T key) {
        int idx = getIdx(key);
        if (idx == -1) {
            return;
        }
        length--;
        System.arraycopy(elements,idx+1,elements,idx,length-idx);
        if (length + 2 * CHUNK < elements.length) {
            elements = Arrays.copyOf(elements, length - CHUNK);
        }
    }

    @Override
    public int occurrences(T key) {
        int idx = getIdx(key);
        if (idx == -1) {
            return 0;
        }
        int result = 1;
        for (int i = idx + 1; i < length && elements[i].compareTo(key) == 0 ; i++) {
            result++;
        }
        for (int i = idx - 1; i >= 0 && elements[i].compareTo(key) == 0 ; i--) {
            result++;
        }
        return result;
    }

    @Override
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        if (leftKey.compareTo(rightKey) > 0 || getMin().compareTo(rightKey) > 0 || getMax().compareTo(leftKey) < 0|| ((!leftIncluded || !rightIncluded) && rightKey.compareTo(leftKey) == 0) || length == 0)
            return Arrays.copyOfRange(elements,0,0);

        int left = getClosestPosition(leftKey);
        int right = getClosestPosition(rightKey);

        if (leftIncluded) {
            while (left - 1 > -1 && elements[left-1].compareTo(leftKey) == 0) {
                left--;
            }
        }
        else {
            while (left < length && elements[left].compareTo(leftKey) == 0) {
                left++;
            }
        }
        if (right != length && this.elements[right].compareTo(rightKey) == 0) {
            if (rightIncluded) {
                while (right + 1 < length && elements[right + 1].compareTo(rightKey) == 0) {
                    right++;
                }
            } else {
                while (right >= 0 && elements[right].compareTo(rightKey) == 0) {
                    right--;
                }
            }
        } else
            right--;


        return Arrays.copyOfRange(elements,left,right+1);
    }

    @Override
    public void sortedPrint() {
        System.out.println(this);
    }

    @Override
    public T getMax() {
        if (length == 0)
            throw new RuntimeException("no hay elementos");
        return elements[length-1];
    }

    @Override
    public T getMin() {
        if (length == 0)
            throw new RuntimeException("no hay elementos");
        return elements[0];
    }

    public T[] toArray() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T t : elements) {
            sb.append(t).append(' ');
        }
        return sb.toString();
    }
}
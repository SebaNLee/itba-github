import java.lang.reflect.Array;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class IndexWithDuplicates<T extends Comparable<? super T>> implements IndexService<T>{
    private Object[] elements = new Object[CHUNK];
    private static final int CHUNK = 8;
    private int dim;


    @Override
    public void initialize(T[] elements) {
        if(elements == null)
            throw new IllegalArgumentException();
        this.elements = Arrays.copyOf(elements, elements.length);
        Arrays.sort(this.elements);
        this.dim = elements.length;
    }

    @Override
    public boolean search(T key) {
        if(dim==0)
            throw new RuntimeException();
        return binarySearch(key, 0, dim-1) >= 0;
    }

    public int binarySearch(T key, int izq, int der) {
        if(izq >= der) {
            if(((T)elements[izq]).compareTo(key)>0)
                return izq-1;
            if(((T)elements[izq]).compareTo(key)<0)
                return izq+1;
            return izq;
        }

        int pivot = (der + izq)/2;

        if(((T)elements[pivot]).compareTo(key)>0)
            return binarySearch(key, izq, pivot-1);
        if(((T)elements[pivot]).compareTo(key)<0)
            return binarySearch(key, pivot+1, der);

        return pivot;
    }

    public int firstOccurrence(T key) {
        int index = binarySearch(key, 0, dim-1);
        if(index>0 && index<dim && ((T)elements[index]).compareTo(key) == 0 ) {
            for(int i=index-1; i>=0; i--) {
                if(((T)elements[i]).compareTo(key) != 0)
                    return i+1;
            }
            return 0;
        }
        return index;
    }

    public int lastOccurrence(T key) {
        int index = binarySearch(key, 0, dim-1);
        if(index>0 && index<dim && ((T)elements[index]).compareTo(key) == 0) {
            for(int i=index+1; i<dim; i++) {
                if(((T)elements[i]).compareTo(key) != 0)
                    return i-1;
            }
            return dim-1;
        }
        return index;
    }

    @Override
    public void insert(T key) {
        if(dim == 0){
            elements[0] = key;
            dim++;
            return;
        }
        int index = lastOccurrence(key);
        if(dim == elements.length)
            elements = Arrays.copyOf(elements, elements.length+CHUNK);
        if(index<0)
            index=0;

        for(int i = dim-1; i>=index; --i) {
            elements[i+1] = elements[i];
        }
        elements[index] = key;
        dim++;
    }

    @Override
    public void delete(T key) {
        int index = lastOccurrence(key);
        if(index>= 0 && index<dim && ((T)elements[index]).compareTo(key) == 0) {
            if (dim - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index, dim - 1 - index);
            if(--dim % CHUNK == 0)
                elements = Arrays.copyOf(elements, elements.length-CHUNK);
        }
    }

    @Override
    public int occurrences(T key) {
        int firstIndex = firstOccurrence(key);
        int lastIndex = lastOccurrence(key);
        if(firstIndex>=0 && firstIndex<dim && ((T)elements[firstIndex]).compareTo(key) == 0)
            return lastIndex-firstIndex+1;
        return 0;
    }

    @Override
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        if(dim==0)
            return (T[]) Array.newInstance(leftKey.getClass(), 0);
        int leftIndex, rightIndex;
        if(leftIncluded) leftIndex = firstOccurrence(leftKey);
        else leftIndex = lastOccurrence(leftKey)+1;
        if(rightIncluded) rightIndex = lastOccurrence(rightKey);
        else rightIndex = firstOccurrence(rightKey)-1;

        int from = Math.max(0,leftIndex), to = Math.min(dim-1, rightIndex);
        if(from>to)
            return (T[]) Array.newInstance(leftKey.getClass(), 0);

        T[] toReturn = (T[]) Array.newInstance(leftKey.getClass(), to-from+1);
        for(int i=from, j=0; i<=to; i++) {
            toReturn[j++] = (T) elements[i];
        }
        return toReturn;
    }

    @Override
    public void sortedPrint() {
        System.out.print("{ ");
        for(int i=0; i<dim; i++)
            System.out.printf("%s ", elements[i].toString());
        System.out.print("}");
    }

    @Override
    public T getMax() {
        if(dim==0)
            throw new RuntimeException();
        return (T)elements[dim-1];
    }

    @Override
    public T getMin() {
        if(dim==0)
            throw new RuntimeException();
        return (T)elements[0];
    }

}
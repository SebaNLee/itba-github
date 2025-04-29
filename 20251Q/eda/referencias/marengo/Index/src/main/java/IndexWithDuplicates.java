import java.util.Arrays;

public class IndexWithDuplicates implements IndexService{
    private int[] elements = new int[CHUNK];
    private static final int CHUNK = 8;
    private int dim;

    @Override
    public void initialize(int[] elements) {
        if(elements == null)
            throw new IllegalArgumentException();
        Arrays.sort(elements);
        this.elements = Arrays.copyOf(elements, elements.length);
        this.dim = elements.length;
    }

    @Override
    public boolean search(int key) {
        return binarySearch(key, 0, dim-1) >= 0;
    }

    @Override
    public void insert(int key) {
        int index = lastOccurrence(key);
        if(dim == elements.length)
            elements = Arrays.copyOf(elements, elements.length+CHUNK);
        for(int i = dim-1; i>=index; --i) {
            elements[i+1] = elements[i];
        }
        dim++;
        elements[index] = key;
    }

    @Override
    public void delete(int key) {
        int index = lastOccurrence(key);
        if(index>= 0 && index<dim && elements[index]==key) {
            for(int i = dim-1; i>index; --i) {
                elements[i-1] = elements[i];
            }
            if(--dim % CHUNK == 0)
                elements = Arrays.copyOf(elements, elements.length-CHUNK);
        }
    }

    @Override
    public int occurrences(int key) {
        int firstIndex = firstOccurrence(key);
        int lastIndex = lastOccurrence(key);
        if(firstIndex>=0 && firstIndex<dim && elements[firstIndex]==key)
            return lastIndex-firstIndex+1;
        return 0;
    }

    @Override
    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
        int leftIndex, rightIndex;
        if(leftIncluded) leftIndex = firstOccurrence(leftKey);
        else leftIndex = lastOccurrence(leftKey)+1;
        if(rightIncluded) rightIndex = lastOccurrence(rightKey);
        else rightIndex = firstOccurrence(rightKey)-1;

        int from = Math.max(0,leftIndex), to = Math.min(dim-1, rightIndex);
        if(from>to)
            return new int[0];

        int[] toReturn = new int[to-from+1];
        for(int i=from, j=0; i<=to; i++) {
                toReturn[j++] = elements[i];
        }
        return toReturn;
    }

    @Override
    public void sortedPrint() {
        System.out.print("{ ");
        for(int i=0; i<dim; i++)
            System.out.printf("%d ", elements[i]);
        System.out.print("}");
    }

    @Override
    public int getMax() {
        if(dim==0)
            throw new RuntimeException();
        return elements[dim-1];
    }

    @Override
    public int getMin() {
        if(dim==0)
            throw new RuntimeException();
        return elements[0];
    }


    public int binarySearch(int key, int izq, int der) {
        if(izq >= der) {
            if(elements[izq] > key)
                return izq-1;
            if(elements[izq] == key)
                return izq;
            return izq+1;
        }

        int pivot = (der + izq)/2;

        if(elements[pivot] > key)
            return binarySearch(key, izq, pivot-1);
        if(elements[pivot] < key)
            return binarySearch(key, pivot+1, der);

        return pivot;
    }

    public int firstOccurrence(int key) {
        int index = binarySearch(key, 0, dim-1);
        if(index>0 && index<dim && elements[index]==key ) {
            for(int i=index-1; i>=0; i--) {
                if(elements[i]!=key)
                    return i+1;
            }
            return 0;
        }
        return index;
    }

    public int lastOccurrence(int key) {
        int index = binarySearch(key, 0, dim-1);
        if(index>0 && index<dim && elements[index]==key) {
            for(int i=index+1; i<dim; i++) {
                if(elements[i]!=key)
                    return i-1;
            }
            return dim-1;
        }
        return index;
    }
}
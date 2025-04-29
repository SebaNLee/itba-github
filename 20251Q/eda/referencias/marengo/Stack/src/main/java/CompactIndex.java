import java.util.Arrays;

public class CompactIndex implements IndexService{
    private IndexCount[] elements = new IndexCount[0];
    private final int chunkSize = 4;
    private int size = 0;

    // Estructura para guardar la cantidad de ocurrencias de cada índice
    class IndexCount {
        String index;
        long count;

        public IndexCount(String index) {
            this.index = index;
            this.count = 1;
        }
    }

    @Override
    public void initialize(String[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements no puede ser null");
        }
        this.elements = new IndexCount[0];
        this.size = 0;

        for(String element : elements) {
            insert(element);
        }
    }

    @Override
    public void insert(String key) {
        // Crecer el arreglo si hace falta
        if (elements.length == size) {
            crecer();
        }

        if(size == 0){
            elements[0] = new IndexCount(key);
            size++;
            return;
        }
        int index = binarySearch(key, 0, size-1);
        if(index>=0 && index<=size-1 && elements[index].index.compareTo(key)==0) {
            elements[index].count++;
            return;
        }

        for(int i = size-1; i>=index; --i) {
            elements[i+1] = elements[i];
        }
        elements[index] = new IndexCount(key);
        size++;
    }

    private int binarySearch(String key, int izq, int der) {
        if(izq >= der) {
            if(elements[izq].index.compareTo(key)<0)
                return izq+1;
            return izq;
        }

        int pivot = (der + izq)/2;

        if(elements[pivot].index.compareTo(key) > 0)
            return binarySearch(key, izq, pivot-1);
        if(elements[pivot].index.compareTo(key) < 0)
            return binarySearch(key, pivot+1, der);

        return pivot;
    }

    @Override
    public long occurrences(String key) {
        int index = binarySearch(key, 0, size-1);
        if(index>=0 && index<=size-1)
            return elements[index].count;

        return 0L;
    }

    @Override
    public String getMax() {
        if (size == 0) {
            return null;
        }
        return elements[size-1].index;
    }

    @Override
    public String getMin() {
        if (size == 0) {
            return null;
        }
        return elements[0].index; //acá lo cambié estaba copypaste size-1
    }

    private void crecer() {
        IndexCount[] newElements = new IndexCount[size + chunkSize];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    public void printElements() {
        for(int i=0; i<size; i++) {
            System.out.format("index: %s count: %d\n", elements[i].index, elements[i].count);
        }
    }
}


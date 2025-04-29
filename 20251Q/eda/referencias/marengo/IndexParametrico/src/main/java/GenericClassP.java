public class GenericClassP<E extends Comparable<E>> {
    private Object[] arreglo;

    public void initialize(int dim) {
        arreglo = new Object[dim];
    }

    public void setElement(int pos, E element) {
        if(pos>=arreglo.length)
            throw new IllegalArgumentException();
        arreglo[pos] = element;
    }

    @SuppressWarnings("unchecked")
    public E getElement(int pos) {
        if(pos>=arreglo.length)
            throw new IllegalArgumentException();
        return (E)arreglo[pos];
    }

    public static void main(String[] args) {
        GenericClassP<Double> auxi = new GenericClassP<>();
        auxi.initialize(5);
        auxi.setElement(3, 10.0);
        auxi.setElement(2, 20.8);
        for (int i= 0; i < 5; i++) {
            System.out.println( auxi.getElement(i) );
        }
    }
}

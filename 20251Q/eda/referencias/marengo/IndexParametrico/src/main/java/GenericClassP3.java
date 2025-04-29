public class GenericClassP3<E> {
    //public class GenericClassP3<E extends Comparable<E>> -> error
    private E[] arreglo;

    @SuppressWarnings("unchecked")
    public void initialize(int dim) {
        arreglo = (E[]) new Object[dim];
    }

    public void setElement(int pos, E element) {
        arreglo[pos] = element;
    }

    public E getElement(int pos) {
        return arreglo[pos];
    }

    public static void main(String[] args) {
        GenericClassP3<Double> auxi = new GenericClassP3<>();
        auxi.initialize(5);
        auxi.setElement(3, 10.0);
        auxi.setElement(2, 20.8);
        for (int i= 0; i < 5; i++) {
            System.out.println( auxi.getElement(i) );
        }
    }

}

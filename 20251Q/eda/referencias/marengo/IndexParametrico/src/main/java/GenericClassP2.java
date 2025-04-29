import java.lang.reflect.Array;

public class GenericClassP2<E extends Comparable<E>> {
    private E[] arreglo;

    @SuppressWarnings("unchecked")
    public void initialize(int dim, Class<E> theClass) {
        arreglo= (E[]) Array.newInstance(theClass, dim);
    }

    public void setElement(int pos, E element) {
        arreglo[pos]= element;
    }

    public E getElement(int pos) {
        return arreglo[pos];
    }

    public static void main(String[] args) {
        GenericClassP2<Double> auxi = new GenericClassP2<>();
        auxi.initialize(5, Double.class);
        auxi.setElement(3, 10.0);
        auxi.setElement(2, 20.8);
        for (int i= 0; i < 5; i++) {
            System.out.println( auxi.getElement(i) );
        }
    }

}
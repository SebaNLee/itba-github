import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

    

    private E[] elements;
    private int dim;
    private static final int INITIAL_DIM = 1;


    @SuppressWarnings("unchecked")
    public ArrayStack() {
        
        // observar que NO se puede hacer lo siguiente:
        // elements = new E[INITIAL_DIM];
        // pues NO se puede instanciar un objeto genérico
    
        elements = (E[]) new Object[INITIAL_DIM];
    }

    @Override
    public boolean isEmpty() {
        return dim == 0;
    }

    @Override
    public void push(E elem) {
        if(dim == elements.length) {
            resize();
        }
        elements[dim++] = elem;
    }

    @Override
    public E pop() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[--dim];
    }
    
    @Override
    public E peek() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[dim - 1];
    }

    private void resize() {
        elements = Arrays.copyOf(elements, elements.length + INITIAL_DIM);
    }
}

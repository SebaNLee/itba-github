@SuppressWarnings("unchecked")
public class QueueImp<T> {
    private final Object[] queue;
    private int first=1, last=0;

    public QueueImp(int dim) {
        queue = new Object[dim];
    }

    public void queue(T element) {
        if(isFull())
            throw new RuntimeException("Esta lleno");
        if(last == queue.length-1)
            last = 0;
        else
            last++;
        queue[last] = element;
    }

    public T dequeue() {
        if(isEmpty())
            throw new RuntimeException("Esta vacio");
        T toReturn = (T) queue[first];
        queue[first] = null;
        if(first == queue.length -1)
            first = 0;
        else
            first++;

        return toReturn;
    }

    public T peek() {
        if(isEmpty())
            throw new RuntimeException("Esta vacio");
        return (T) queue[first];
    }

    public boolean isEmpty() {
        return queue[first] == null;
    }

    public boolean isFull() {
        return (last==first-1 || (last==queue.length && first==0)) && queue[last] != null;
    }

    public int size() {
        return Math.abs(first-(last+1));
    }
}

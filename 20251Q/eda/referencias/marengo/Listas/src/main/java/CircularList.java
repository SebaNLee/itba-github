import java.util.NoSuchElementException;

public class CircularList<T> {
    private Node root;
    private Node last;
    private int size;

    public void queue(T element) {
        if (element == null)
            throw new IllegalArgumentException("element cannot be null");

        if(isEmpty()) {
            root = new Node(element);
            last = root;
        }
        else {
            last.next = new Node(element, root);
            last = last.next;
        }
        size++;
    }

    public T dequeue() {
        if(isEmpty())
            throw new NoSuchElementException();

        Node toReturn = root;
        last.next = root.next;
        root = last.next;
        size--;
        return toReturn.data;
    }

    public T peek() {
        if(isEmpty())
            throw new NoSuchElementException();

        return root.data;
    }

    public boolean isEmpty() {
        return root==null;
    }

    public int size() {
        return size;
    }

    public void dump() {
        Node current = root;
        while(current != null) {
            System.out.println(current.data);
            current = current.next;
            if(current == root)
                return;
        }
    }

    private final class Node {
        private final T data;
        private Node next;

        private Node(T data) {
            this.data= data;
        }

        private Node(T data, Node next) {
            this.data= data;
            this.next= next;
        }
    }
}

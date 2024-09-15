package ar.edu.itba.poo.soltp5.ej3.list;

public class LinearListImpl<T> implements LinearList<T> {

    private Node<T> first;
    private int dim;

    @Override
    public void add(T obj) {
        Node<T> current = first;
        if (first == null) {
            first = new Node<>(obj, null);
        } else {
            while (current.tail != null) {
                current = current.tail;
            }
            current.tail = new Node<>(obj, null);
        }
        dim++;
    }

    @Override
    public T get(int i) {
        Node<T> current = findNode(i);
        return current.head;
    }

    @Override
    public int indexOf(T obj) {
        int index = 0;
        Node<T> current = first;
        while (current != null && !current.head.equals(obj)) {
            current = current.tail;
            index++;
        }
        return current == null ? -1 : index;
    }

    @Override
    public void remove(int i) {
        if (i < 0 || first == null) {
            throw new IndexOutOfBoundsException();
        }
        if (i == 0) {
            first = first.tail;
        } else {
            Node<T> previous = findNode(i-1);
            if (previous.tail == null) {
                throw new IndexOutOfBoundsException();
            }
            previous.tail = previous.tail.tail;
        }
        dim--;
    }

    @Override
    public void set(int i, T obj) {
        Node<T> node = findNode(i);
        node.head = obj;
    }

    @Override
    public int size() {
        return dim;
    }

    private Node<T> findNode(int i) {
        Node<T> current = first;
        int index = 0;
        if (i < 0) {
            throw new IndexOutOfBoundsException();
        }
        while (current != null && index < i) {
            index++;
            current = current.tail;
        }
        if (current == null) {
            throw new IndexOutOfBoundsException();
        }
        return current;
    }

    private static class Node<T> {

        private T head;
        private Node<T> tail;

        Node(T head, Node<T> tail) {
            this.head = head;
            this.tail = tail;
        }

    }

}

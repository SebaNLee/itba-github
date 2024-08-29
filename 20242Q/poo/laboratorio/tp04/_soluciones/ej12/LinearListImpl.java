package ar.edu.itba.poo.soltp4.ej12;

public class LinearListImpl implements LinearList {

    private Node first;
    private int size;

    @Override
    public void add(Object obj) {
        Node current = first;
        if (first == null) {
            first = new Node(obj, null);
        } else {
            while (current.tail != null) {
                current = current.tail;
            }
            current.tail = new Node(obj, null);
        }
        size++;
    }

    @Override
    public Object get(int i) {
        Node current = findNode(i);
        return current.head;
    }

    @Override
    public int indexOf(Object obj) {
        int index = 0;
        Node current = first;

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
            Node previous = findNode(i-1);
            if (previous.tail == null) {
                throw new IndexOutOfBoundsException();
            }
            previous.tail = previous.tail.tail;
        }
        size--;
    }

    @Override
    public void set(int i, Object obj) {
        Node node = findNode(i);
        node.head = obj;
    }

    @Override
    public int size() {
        return size;
    }

    private Node findNode(int i) {
        Node current = first;
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

    private static class Node {

        private Object head;
        private Node tail;

        Node(Object head, Node tail) {
            this.head = head;
            this.tail = tail;
        }

    }

}


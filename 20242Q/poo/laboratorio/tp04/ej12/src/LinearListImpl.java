

public class LinearListImpl implements LinearList {

    Node firstNode;

    // sin constructor

    @Override
    public void add(Object obj) {
        
        Node current = firstNode;

        if (current == null) {
            // Si la lista está vacía, agregamos el primer nodo
            firstNode = new Node(obj, null);
        } else {
            // Si no, conectamos el nuevo nodo al final
            while(current.tail != null) {
                current = current.tail;
            }
            current.tail = new Node(obj, null);
        }        
    }

    @Override
    public Object get(int i) {
        Node current = findNode(i);
        return current.head;
    }

    @Override
    public void set(int i, Object obj) {
        Node node = findNode(i);
        node.head = obj;
    }

    @Override
    public void remove(int i) {

        if(i < 0 || firstNode == null) {
            throw new IndexOutOfBoundsException();
        }

        if(i == 0) {
            firstNode = firstNode.tail;
        }
        else {
            Node previousNode = findNode(i - 1);

            if(previousNode == null) {
                throw new IndexOutOfBoundsException();
            }

            previousNode.tail = previousNode.tail.tail;
        }
    }

    // devuelve -1 si no encuentra
    @Override
    public int indexOf(Object obj) {
        int index = 0;
        Node current = firstNode;

        while(current != null && !current.head.equals(obj)) {
            current = current.tail;
            index++;
        }

        return current == null ? -1 : index;
    }

    @Override
    public int size() {
        Node current = firstNode;
        int size = 0;
        
        while(current != null) {
            size++;
            current = current.tail;
        }

        return size;
    }

    private Node findNode(int i) {
        Node current = firstNode;
        int index = 0;

        if(i < 0) {
            throw new IndexOutOfBoundsException();
        }

        while(current != null && index < i) {
            index++;
            current = current.tail;
        }

        if(current == null) {
            throw new IndexOutOfBoundsException();
        }

        return current;
    }

    private class Node { // !!?? no le pongo static todavía
        
        private Object head;
        private Node tail;

        Node(Object head, Node tail) { // package private
            this.head = head;
            this.tail = tail;
        }
    }

}

package core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {
    private Node<T> root;
    private int size;
    private Traversal traversal = Traversal.BYLEVELS;

    @Override
    public void insert(T myData) {
        if(root==null)
            root = new Node<>(myData);
        else
            root.insert(myData);
        size++;
    }

    @Override
    public String preOrder() {
        if(root==null)
            throw new IllegalStateException();
        return root.preorder(new StringBuilder());
    }

    @Override
    public String postOrder() {
        if(root==null)
            throw new IllegalStateException();
        return root.postorder(new StringBuilder());
    }

    @Override
    public String inOrder() {
        if(root==null)
            throw new IllegalStateException();
        return root.inorder(new StringBuilder());
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    protected void setRoot(Node<T> node) {
        root = node;
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public boolean contains(T myData) {
        if(root==null)
            return false;
        return root.hasChildren(myData);
    }

    @Override
    public T getMax() {
        Node<T> current = root;
        while(current!=null){
            if (current.right==null)
                return current.data;
            current = current.right;
        }
        return null;
    }

    @Override
    public T getMin() {
        Node<T> current = root;
        while(current!=null){
            if (current.left==null)
                return current.data;
            current = current.left;
        }
        return null;
    }

    @Override
    public String printByLevels() {
        if(root==null)
            return "";

        StringBuilder s = new StringBuilder();
        Queue<Node<T>> pendingNodes = new LinkedList<>();
        pendingNodes.add(root);
        int auxSize = size;
        while(!pendingNodes.isEmpty()) {
            Node<T> currentNode = pendingNodes.remove();
            s.append(currentNode.data).append(" ");
            if(currentNode.left!=null)
                pendingNodes.add(currentNode.left);
            if(currentNode.right!=null)
                pendingNodes.add(currentNode.right);
        }
        return s.toString();
    }

    @Override
    public void delete(T myData) {
        if(myData==null)
            throw new RuntimeException("Element cannot be null");

        if(root!=null)
            root = root.delete(myData);
    }

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public int getOccurrences(T element) {
        if(root==null)
            return 0;
        return root.getOccurrences(element);
    }

    @Override
    public T kEsimo(int k) {
        if(k>size || k<=0)
            return null;
        Node<T> node = new Node<>(null);
        root.kEsimo(k, new int[]{0},node);
        return node.data;
    }

    @Override
    public T getCommonNode(T element1, T element2) {
        if(root!=null) {
            return root.getCommonNode(element1,element2);
        }
        return null;
    }

    @Override
    public T getCommonNodeWithRepeated(T element1, T element2) {
        if(root!=null) {
            return root.getCommonNodeWithRepeated(element1,element2);
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return switch (traversal) {
            case BYLEVELS -> new BSTByLevelIterator();
            case INORDER -> new BSTInOrderIterator();
            case POSTORDER -> new BSTPostOrderIterator();
            case PREORDER -> new BSTPreOrderIterator();
        };
    }


    class BSTPreOrderIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    class BSTPostOrderIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    class BSTInOrderIterator implements Iterator<T> {
        Stack<NodeTreeInterface<T>> stack;
        NodeTreeInterface<T> current;

        public BSTInOrderIterator() {
            stack= new Stack<>();
            current= root;
        }

        @Override
        public boolean hasNext() {
            return ! stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while(current != null) {
                stack.push(current);
                current= current.getLeft();
            }
            NodeTreeInterface<T> elementToProcess= stack.pop();
            current= elementToProcess.getRight();
            return elementToProcess.getData();
        }
    }

    class BSTByLevelIterator implements Iterator<T> {
        private Queue<NodeTreeInterface<T>> queue;

        private BSTByLevelIterator() {
            queue = new LinkedList<>();
            if(root!=null)
                queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            NodeTreeInterface<T> currentNode = queue.remove();

            if(currentNode.getLeft()!=null)
                queue.add(currentNode.getLeft());
            if(currentNode.getRight()!=null)
                queue.add(currentNode.getRight());

            return currentNode.getData();
        }
    }

    static class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node(T data) {
            this(data,null,null);
        }

        @Override
        public T getData() {
            return data;
        }

        private int getHeight(){
            if (left == null && right == null) {
                return 0;
            } else {
                int leftHeight = (left==null? 0: left.getHeight());
                int rightHeight = (right==null? 0: right.getHeight());
                return Math.max(leftHeight ,rightHeight) + 1;
            }
        }

        private T getCommonNode(T element1, T element2) {
            if(left!=null && data.compareTo(element1)>0 && data.compareTo(element2)>0) {
                //left puede ser mas cercano
                return left.getCommonNode(element1,element2);
            }
            else if(right!=null && data.compareTo(element1)<0 && data.compareTo(element2)<0) {
                //right puede ser mas cercano
                return right.getCommonNode(element1,element2);
            }
            else {
                //yo soy el mas cercano
                if(element1.compareTo(element2)!=0 && hasChildren(element1) && hasChildren(element2) )
                    return data;
                return null;
            }
        }

        private T getCommonNodeWithRepeated(T element1, T element2) {
            if(left!=null && data.compareTo(element1)>0 && data.compareTo(element2)>0) {
                //left puede ser mas cercano
                return left.getCommonNodeWithRepeated(element1,element2);
            }
            else if(right!=null && data.compareTo(element1)<0 && data.compareTo(element2)<0) {
                //right puede ser mas cercano
                return right.getCommonNodeWithRepeated(element1,element2);
            }
            else {
                //yo soy el mas cercano
                if(element1.compareTo(element2)!=0 && hasChildren(element1) && hasChildren(element2))
                    return data;
                if(element1.compareTo(element2)==0 && getOccurrences(element1)>=2)
                    return data;
                return null;
            }
        }

        private void kEsimo(int k, int[] count, Node<T> myData) {
            if(left!=null)
                left.kEsimo(k,count, myData);

            if(++count[0]==k)
                myData.data = data;

            if(right!=null)
                right.kEsimo(k,count, myData);
        }

        private int getOccurrences(T element) {
            if (data.compareTo(element) == 0)
                return 1+(left!=null?left.getOccurrences(element):0);
            else if (data.compareTo(element) < 0)
                return right!=null?right.getOccurrences(element):0;

            return left!=null?left.getOccurrences(element):0;
        }

        private Node<T> delete(T myData) {
            if(myData.compareTo(data)<0) {
                if (left != null)
                    left = left.delete(myData);
                return this;
            }
            if(myData.compareTo(data)>0) {
                if (right != null)
                    right = right.delete(myData);
                return this;
            }

            if(left==null)
                return right;
            if(right==null)
                return left;

            this.data = lexiAdjacent(left);
            left = left.delete(data);
            return this;
        }

        private T lexiAdjacent(Node<T> candidate) {
            Node<T> aux=candidate;
            while(aux.right != null)
                aux=aux.right;
            return aux.data;
        }

        private boolean hasChildren(T myData) {
            if(data.compareTo(myData)==0)
                return true;
            if(data.compareTo(myData)>0 && left!=null)
                return left.hasChildren(myData);
            if(data.compareTo(myData)<0 && right!=null)
                return right.hasChildren(myData);
            return false;
        }

        private String preorder(StringBuilder s) {
            s.append(data).append(" "); //listar
            if(left!=null)
                left.preorder(s); //preorder izq
            if(right!=null)
                right.preorder(s); //preorder der
            return s.toString();
        }

        private String postorder(StringBuilder s) {
            if(left!=null)
                left.postorder(s);
            if(right!=null)
                right.postorder(s);
            s.append(data).append(" ");
            return s.toString();
        }

        private String inorder(StringBuilder s) {
            if(left!=null)
                left.inorder(s);
            s.append(data).append(" ");
            if(right!=null)
                right.inorder(s);

            return s.toString();
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }

        public void insert(T myData) {
            if(myData.compareTo(data)<=0) {
                if(left==null)
                    left = new Node<>(myData);
                else
                    left.insert(myData);
            }
            else {
                if(right==null)
                    right = new Node<>(myData);
                else
                    right.insert(myData);
            }
        }
    }

    public static void main(String[] args) {
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(40);
        myTree.forEach(System.out::println);
        System.out.println("---");
        myTree.setTraversal(Traversal.INORDER);
        myTree.forEach(System.out::println);
    }
}

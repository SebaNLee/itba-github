package core;

import java.util.LinkedList;
import java.util.Queue;

public class AVL<T extends Comparable<? super T>> implements AVLTreeInterface<T> {
    private Node<T> root;
    private int size;

    @Override
    public void insert(T myData) {
        if(myData == null)
            throw new RuntimeException();

        root = insert(root, myData);
    }

    private Node<T> insert(Node<T> currentNode, T myData) {
        if(currentNode == null)
            return new Node<>(myData);

        if(myData.compareTo(currentNode.data) <= 0)
            currentNode.left = insert(currentNode.left, myData);
        else
            currentNode.right = insert(currentNode.right, myData);

        int i = currentNode.left == null?-1:currentNode.left.height;
        int d = currentNode.right == null?-1:currentNode.right.height;
        currentNode.height = 1 + Math.max(i, d);

        int balance = getBalance(currentNode);

        if(balance < -1) {
            if(getBalance(currentNode.right) > 0) {
                currentNode.right = rightRotate(currentNode.right);
                System.out.println("right");
                System.out.println(root);
            }
            System.out.println("left");
            return leftRotate(currentNode);
        }
        else if(balance > 1) {
            if(getBalance(currentNode.left) < 0) {
                currentNode.left = leftRotate(currentNode.left);
                System.out.println("left");
                System.out.println(root);
            }
            System.out.println("right");
            return rightRotate(currentNode);
        }

        return currentNode;
    }

    private Node<T> leftRotate(Node<T> currentNode) {
        Node<T> r = currentNode.right;
        System.out.print("pivote" + currentNode.data + "\n");
        currentNode.right = r.left;
        r.left = currentNode;
        currentNode.height = Math.max(height(currentNode.left), height(currentNode.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }

    private Node<T> rightRotate(Node<T> currentNode) {
        Node<T> r = currentNode.left;
        System.out.print("pivote" + currentNode.data + "\n");
        currentNode.left = r.right;
        r.right = currentNode;
        currentNode.height = Math.max(height(currentNode.left), height(currentNode.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }

    private int height(Node<T> currentNode) {
        if (currentNode == null)
            return 0;
        return currentNode.height;
    }

    private int getBalance(Node<T> currentNode) {
        if(currentNode == null)
            return 0;
        return height(currentNode.left) - height(currentNode.right);
    }

    @Override
    public boolean find(T myData) {
        if(root==null)
            return false;
        return root.hasChildren(myData);
    }

    @Override
    public String toString() {
        return root.toString();
    }


    static class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private int height;

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 1;
        }

        public Node(T data) {
            this(data, null, null);
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            print(buffer, "", "");
            return buffer.toString();
        }

        private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(data);
            buffer.append('\n');
            if(left!=null)
                left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            if(right!=null)
                right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
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
    }
}

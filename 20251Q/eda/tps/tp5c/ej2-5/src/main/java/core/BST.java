package core;

public class BST<T extends Comparable<? super T>> implements BSTreeInterface<T> {

    private Node<T> root;
    private int size;

    // constructo default

    @Override
    public void insert(T myData) {
        if(root == null)
            root = new Node<>(myData);
        else
            root.insert(myData);
        size++;
    }

    @Override
	public void preOrder() {
        System.out.println(root.preOrder(new StringBuilder()));
    }

    @Override
	public void postOrder() {
        System.out.println(root.postOrder(new StringBuilder()));
    }

    @Override
	public void inOrder() {
        System.out.println(root.inOrder(new StringBuilder()));
    }

    @Override
	public NodeTreeInterface<T> getRoot() {
        return root;
    }
	
    @Override
	public int getHeight() {
        return root.getHeight();
    }





    static class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T> {

        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this(data, null, null);
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
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

        public void insert(T myData) {
            if(myData.compareTo(data) <= 0) {
                if(left == null)
                    left = new Node<>(myData);
                else
                    left.insert(myData);
            }
            else {
                if(right == null)
                    right = new Node<>(myData);
                else
                    right.insert(myData);
            }
        }

        public int getHeight(){
            if (left == null && right == null) {
                return 0;
            }
            else {
                int leftHeight = (left==null ? 0: left.getHeight());
                int rightHeight = (right==null ? 0: right.getHeight());
                return Math.max(leftHeight ,rightHeight) + 1;
            }
        }

        public String preOrder(StringBuilder s) {
            s.append(data).append(" "); //listar
            if(left!=null)
                left.preOrder(s); //preorder izq
            if(right!=null)
                right.preOrder(s); //preorder der
            return s.toString();
        }

        public String postOrder(StringBuilder s) {
            if(left!=null)
                left.postOrder(s);
            if(right!=null)
                right.postOrder(s);
            s.append(data).append(" ");
            return s.toString();
        }

        public String inOrder(StringBuilder s) {
            if(left!=null)
                left.inOrder(s);
            s.append(data).append(" ");
            if(right!=null)
                right.inOrder(s);

            return s.toString();
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

        myTree.inOrder();
        myTree.preOrder();
        myTree.postOrder();

        // myTree.forEach(System.out::println);
        // System.out.println("---");
        // myTree.setTraversal(Traversal.INORDER);
        // myTree.forEach(System.out::println);
    }
}


// mvn compile && mvn exec:java -Dexec.mainClass="core.BST"
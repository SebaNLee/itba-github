package slee;

import java.util.ArrayList;


public class BST<T extends Comparable<? super T>> {

        private Node root;

        public void insert(T myData){
            if ( root == null ) {
                root = new Node( myData );
                return;
            }
            root.insert( myData );
        }

        private class Node {
            private T data;
            private Node left;
            private Node right;

            Node( T d ){ data = d; }

            public void insert(T myData){
                if ( data.compareTo(myData) >= 0 ){
                    if ( left == null )
                        left = new Node( myData );
                    else
                        left.insert( myData );
                } else {
                    if ( right == null )
                        right = new Node( myData );
                    else
                        right.insert( myData );
                }
            }
        }


    int getDiameter(){
        // TODO: Ej 2.a

        // veo que el diámetro es altura derecha + altura izquierda
        // algo que hicimos en clase (getHeight)

        // lo hago recursivamente

        return getDiameterAux(root);
    }

    // para Ej 2.a
    private int getDiameterAux(Node node) {
        if(node == null) {
            return 0;
        }

        int leftDiameter = getDiameterAux(node.left);
        int rightDiameter = getDiameterAux(node.right);

        int currentDiameter = getHeight(node.left) + getHeight(node.right);

        return Math.max(currentDiameter, Math.max(leftDiameter, rightDiameter));
    }

    // para Ej 2.a
	private int getHeight(Node node) {
		if (node == null || node.data == null)
			return 0;

		int leftHeight = getHeight(node.left);
		int rightHeight = getHeight(node.right);

		return Math.max(leftHeight, rightHeight) + 1;
	}



    ArrayList<T> getDiameterPath(){
        // TODO: ej 2.b

        // me fijo primero cuál nodo es con el que tengo que trabajar
        int diameter = getDiameter();

        Node diameterRootNode = findRootNode(root, diameter);

        ArrayList<T> toReturn = new ArrayList<>();

        return getDiameterPathAux(diameterRootNode, toReturn);
    }

    // para Ej 2.b
    private Node findRootNode(Node node, int diameter) {
        if(getDiameterAux(node) == diameter) {
            return node;
        }
        else {
            if(node.right != null)
                return findRootNode(node.right, diameter);
            if(node.left != null)
                return findRootNode(node.right, diameter);
        }

        return null;
    }

    private ArrayList<T> getDiameterPathAux(Node node, ArrayList<T> array) {

        // esto devuelve valores de más
        // me quedé sin tiempo acá...

        if(node.left != null)
            getDiameterPathAux(node.left, array);
        
        array.add(node.data);

        if(node.right != null)
            getDiameterPathAux(node.right, array);

        return array;
    }






    public static void test1(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void test2(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(5);
        myTree.insert(8);
        myTree.insert(7);
        myTree.insert(42);
        myTree.insert(43);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void main(String[] args) {
        System.out.println( "Test1" );
        test1();
        System.out.println( "Test2" );
        test2();
    }
}


// mvn compile && mvn exec:java -Dexec.mainClass="slee.BST"
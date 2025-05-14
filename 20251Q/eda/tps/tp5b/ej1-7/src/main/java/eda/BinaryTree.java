package eda;


import java.io.FileNotFoundException;      
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class BinaryTree implements BinaryTreeService {
	
	private Node root;

	private Scanner inputScanner;

	public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
		 InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

		 if (is == null)
			 throw new FileNotFoundException(fileName);
		 
		 inputScanner = new Scanner(is);
		 inputScanner.useDelimiter("\\s+");

		 buildTree();
		
		 inputScanner.close();
	}
	

	
	private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {	
	
		 Queue<NodeHelper> pendingOps= new LinkedList<>();
		 String token;
		 
		 root= new Node();
		 pendingOps.add(new NodeHelper(root, NodeHelper.Action.CONSUMIR));
		 
		 while(inputScanner.hasNext()) {
			 
			 token= inputScanner.next();

			 NodeHelper aPendingOp = pendingOps.remove();
			 Node currentNode = aPendingOp.getNode();
			 
			 if ( token.equals("?") ) {
				 // no hace falta poner en null al L o R porque ya esta con null
				 
			 // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
				pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera izq
				pendingOps.add( new NodeHelper(null, NodeHelper.Action.CONSUMIR) );  // como si hubiera der
			 }
			 else {
				 switch (aPendingOp.getAction()) 
				 {
				 	case LEFT: 
				 		currentNode = currentNode.setLeftTree(new Node() );
				 		break;
				 	case RIGHT:
				 		currentNode = currentNode.setRightTree(new Node());
				 		break;
				 }
				 
			 
				 // armo la info del izq, der o el root
				 currentNode.data= token;

				 
				 // hijos se postergan
				 pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.LEFT));
				 pendingOps.add(new NodeHelper(currentNode, NodeHelper.Action.RIGHT));
			 }
	
				 
		 }
			
		 if (root.data == null)  // no entre al ciclo jamas 
			 root= null;
		 
	 }
	
    
	@Override
	public void preorder() {
		preorder(root);
		System.out.println();
	}
	 
	private void preorder(Node node) {
		if (node == null || node.data == null) 
			return;
	 
		System.out.print(node.data + " ");
		preorder(node.left);
		preorder(node.right);
	}
	

	
	@Override
	public void postorder() {
		postorder(root);
		System.out.println();
	}
	
	private void postorder(Node node) {
		if (node == null || node.data == null)
			return;
	
		postorder(node.left);
		postorder(node.right);
		System.out.print(node.data + " ");
	}

	@Override
	public void printHierarchy() {
		printHierarchy(root, "");
	}

	private void printHierarchy(Node node, String prefix) {
		if (node == null || node.data == null) {
			System.out.println(prefix + "├── " + "null");
			return;
		}

		// imprimo nodo actual (y bajo de renglón)
		System.out.println(prefix + "├── " + node.data);

		// le agrego espaciado
		String newPrefix = prefix + "   ";

		// Imprimir hijos
		if (node.left != null || node.right != null) {
			printHierarchy(node.left, newPrefix);
			printHierarchy(node.right, newPrefix);
		}
	}



	// ej4 TODO (anda mal)
	@Override
	public void toFile(String filename) {
		try (PrintWriter writer = new PrintWriter(filename)) {
			Queue<Node> queue = new LinkedList<>();
			queue.add(root);

			while (!queue.isEmpty()) {
				Node current = queue.poll();

				if (current == null || current.data == null) {
					writer.print("? ");
				} else {
					writer.print(current.data + " ");
					queue.add(current.left);
					queue.add(current.right);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}
	

	
	// Ej5 TODO
	private boolean equals(Node a, Node b) {
	if (a == null && b == null) return true;
	if (a == null || b == null) {
		System.out.println("Uno de los nodos es null: " + a + ", " + b);
		return false;
	}
	if (a.data == null && b.data == null) return true;
	if (a.data == null || b.data == null || !a.data.equals(b.data)) {
		System.out.println("Datos distintos: " + a.data + ", " + b.data);
		return false;
	}
	return equals(a.left, b.left) && equals(a.right, b.right);
	}

	

	public int getHeight() {
		return getHeight(root);
	}

	private int getHeight(Node node) {
		if (node == null || node.data == null)
			return 0;

		int leftHeight = getHeight(node.left);
		int rightHeight = getHeight(node.right);

		return Math.max(leftHeight, rightHeight);
	}




	// hasta el get() no se evalua
	class Node {
		private String data;
		private Node left;
		private Node right;
		
		public Node setLeftTree(Node aNode) {
			left= aNode;
			return left;
		}
		
		
		public Node setRightTree(Node aNode) {
			right= aNode;
			return right;
		}
		
		
		
		public Node() {
			// TODO Auto-generated constructor stub
		}

		private boolean isLeaf() {
			return left == null && right == null;
		}


	}  // end Node class

	
	static class NodeHelper {
		
		static enum Action { LEFT, RIGHT, CONSUMIR };
		
		
		private Node aNode;
		private Action anAction;
		
		public NodeHelper(Node aNode, Action anAction ) {
			this.aNode= aNode;
			this.anAction= anAction;
		}
		
		
		public Node getNode() {
			return aNode;
		}
		
		public Action getAction() {
			return anAction;
		}
		
	}
	

	
	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		BinaryTreeService rta = new BinaryTree("data0_1");
		rta.preorder();
		rta.postorder();
	
		BinaryTreeService rta2 = new BinaryTree("data0_3");
		rta2.printHierarchy();
		
		System.out.println(rta2.getHeight());

		rta2.toFile("salida.txt");

		BinaryTreeService rta3 = new BinaryTree("data0_3");

		System.out.println(rta2.equals(rta3));
	}

}  


// mvn compile && mvn exec:java -Dexec.mainClass="eda.BinaryTree"
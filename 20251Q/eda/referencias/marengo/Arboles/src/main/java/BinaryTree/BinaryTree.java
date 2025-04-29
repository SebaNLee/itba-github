package BinaryTree;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;


public class BinaryTree<T> implements BinaryTreeService<T> {
	private Node<T> root;
	private final Scanner inputScanner;
	private int size;
	private int positionLastNode;
	private final Class<?> componentType;

	public BinaryTree(String fileName, Class<?> componentType) throws IllegalArgumentException, SecurityException, FileNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		 InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
		 if (is == null)
			 throw new FileNotFoundException(fileName);
		 this.componentType = componentType;
		 inputScanner = new Scanner(is);
		 inputScanner.useDelimiter("\\s+");
		 buildTree();
		 inputScanner.close();
	}

	@SuppressWarnings("unchecked")
	private void buildTree() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Queue<NodeHelper<T>> pendingOps= new LinkedList<>();
		String token;
		root = new Node<>();
		pendingOps.add(new NodeHelper<T>(root, (Node<T> n)->(n) ));

		while(inputScanner.hasNext()) {
			token= inputScanner.next();
			NodeHelper<T> aPendingOp = pendingOps.remove();
			Node<T> currentNode = aPendingOp.getNode();
			positionLastNode++;

			if ( token.equals("?") ) {
				// no hace falta poner en null al L o R porque ya esta con null
				// reservar el espacio aunque NULL no tiene hijos
				pendingOps.add( new NodeHelper<>(null, null)); // como si hubiera izq. o null
				pendingOps.add( new NodeHelper<>(null, null)); // como si hubiera der. o null
			}
			else {
				Function<Node<T>, Node<T>> anAction= aPendingOp.getAction();
				currentNode= anAction.apply(currentNode);

				// armo la info del izq, der o el root
				Constructor<?> cons = componentType.getConstructor(String.class);
				currentNode.data = (T) cons.newInstance(token) ;
				size++;
				// hijos se postergan
				// version lambda
				pendingOps.add(new NodeHelper<>(currentNode, (Node<T> n)->(n.setLeftTree(new Node<>() )) ));
				pendingOps.add(new NodeHelper<>(currentNode, (Node<T> n)->(n.setRightTree(new Node<>() )) ));

			}
		}
		if (root.data == null) // no entre al ciclo jamas
			root= null;
	}

	@Override
	public String printByLevels() {
		StringBuilder s = new StringBuilder();
		Queue<Node<T>> pendingNodes = new LinkedList<>();
		pendingNodes.add(root);
		int auxSize = size;
		while(auxSize!=0) {
			Node<T> currentNode = pendingNodes.remove();
			if(currentNode!=null) {
				auxSize--;
				s.append(currentNode.data).append(" ");
				pendingNodes.add(currentNode.left);
				pendingNodes.add(currentNode.right);
			}
			else {
				s.append("? ");
				pendingNodes.add(null);
				pendingNodes.add(null);
			}
		}
		return s.toString();
	}

	@Override
	public String toString() {
		return root.toString();
	}

	@Override
	public int getHeight() {
		double result = Math.log(positionLastNode+1) / Math.log(2);
		return (int)Math.ceil(result-1);
	}

	@Override
	public String preorder() {
		if(root==null)
			throw new IllegalStateException();
		return root.preorder(new StringBuilder());
	}

	@Override
	public String postorder() {
		if(root==null)
			throw new IllegalStateException();
		return root.postorder(new StringBuilder());
	}

	@Override
	public String postorder2() {
		if(root==null)
			throw new IllegalStateException();
		return root.postorder2(new StringBuilder());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BinaryTree<?> that)) return false;
		if(root==null)
			return that.root==null;
		return root.equals(that.root);
	}

	@Override
	public int hashCode() {
		return Objects.hash(root, inputScanner, size, positionLastNode);
	}

	@Override
	public int getSize() {
		return size;
	}

	// hasta el get() no se evalua
	static class Node<T> {
		private T data;
		private Node<T> left;
		private Node<T> right;
		
		public Node<T> setLeftTree(Node<T> aNode) {
			left= aNode;
			return left;
		}
		
		public Node<T> setRightTree(Node<T> aNode) {
			right= aNode;
			return right;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Node<?> node)) return false;
			return data.equals(node.data)
					&& (left==null && node.left==null || (left!=null && left.equals(node.left)))
					&& (right==null && node.right==null || (right!=null && right.equals(node.right)));
		}

		@Override
		public int hashCode() {
			return Objects.hash(data, left, right);
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

		private String postorder2(StringBuilder s) {
			s.append(data).append(" ");
			if(right!=null)
				right.postorder2(s);
			if(left!=null)
				left.postorder2(s);
			return s.toString();
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

		private boolean isLeaf() {
			return left == null && right == null;
		}
	}  // end Node class

	
	static class NodeHelper<T> {
		private Node<T> aNode;
		private Function<Node<T>,Node<T>> anAction;
		
		public NodeHelper(Node<T> aNode, Function<Node<T>,Node<T>> anAction ) {
			this.aNode= aNode;
			this.anAction= anAction;
		}

		public Node<T> getNode() {
			return aNode;
		}
		
		public Function<Node<T>,Node<T>> getAction() {
			return anAction;
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		BinaryTreeService<String> rta = new BinaryTree<>("data0_2", String.class);
		System.out.println(rta);
		System.out.println(rta.postorder());
		System.out.println(rta.postorder2());
	}
}
package BinaryTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BinaryOperator;


public class ExpTree implements ExpressionService {

	private Node root;
	private Map<String, BinaryOperator<Double>> operatorMap = new HashMap<>();

	public ExpTree() {
	    System.out.print("Introduzca la expresion en notacion infija con todos los parentesis y blancos: ");

		// token analyzer
	    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
	    String line= inputScanner.nextLine();
	    inputScanner.close();
		operatorMap.put("+", Double::sum);
		operatorMap.put("*", (x,y) -> x*y);
		operatorMap.put("/", (x,y) -> x/y);
		operatorMap.put("-", (x,y) -> x-y);
		operatorMap.put("^", Math::pow);

	    buildTree(line);
	}

	private void buildTree(String line) {
		  // space separator among tokens
		  Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		  root = new Node(lineScanner);
		  lineScanner.close();
	}

	@Override
	public double eval() {
		if(root==null)
			throw new IllegalStateException();
		return root.eval(operatorMap);
	}

	@Override
	public String preorder() {
		if(root==null)
			throw new IllegalStateException();
		return root.preorder(new StringBuilder());
	}

	@Override
	public String inorder() {
		if(root==null)
			throw new IllegalStateException();
		return root.inorder(new StringBuilder());
	}

	@Override
	public String postorder() {
		if(root==null)
			throw new IllegalStateException();
		return root.postorder(new StringBuilder());
	}


	@Override
	public String toString() {
		return root.toString();
	}

	static final class Node {
		private String data;
		private Node left, right;
		
		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner= theLineScanner;
			buildExpression();
		}

		private void buildExpression() {
			if(lineScanner.hasNext("\\(")) {
				lineScanner.next();
				left = new Node(lineScanner);
				data = lineScanner.next(); //si hay next tira NoSuchElement
				right = new Node(lineScanner);
				if(lineScanner.hasNext("\\)"))
					lineScanner.next();
				else
					throw new IllegalArgumentException("Faltan )");
			}
			else if(lineScanner.hasNext("(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?")) {
				data = lineScanner.next();
				left = null;
				right = null;
			}
			else
				throw new IllegalArgumentException("Expresion invalida");
		}

		private double eval(Map<String, BinaryOperator<Double>> operatorNodeMap) {
			if(right!=null & left!=null) {
				return operatorNodeMap.get(data).apply(right.eval(operatorNodeMap), left.eval(operatorNodeMap));
			}
			else {
				return Double.parseDouble(data);
			}
		}

		private String preorder(StringBuilder s) {
			s.append(data).append(" ");
			if(left!=null)
				left.preorder(s);
			if(right!=null)
				right.preorder(s);
			return s.toString();
		}

		private String inorder(StringBuilder s) {
			if(left!=null){
				s.append("( ");
				left.inorder(s);
			}
			s.append(data).append(" ");
			if(right!=null){
				right.inorder(s);
				s.append(") ");
			}
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

		@Override
		public String toString() {
			StringBuilder buffer = new StringBuilder(50);
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

	}  // end Node class
	
	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();
		System.out.println(myExp);
//		System.out.println(myExp.preorder());
//		System.out.println(myExp.postorder());
//		System.out.println(myExp.inorder());
//		System.out.println(myExp.eval());
	}

}  // end BinaryTree.ExpTree class

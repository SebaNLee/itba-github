import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BinaryOperator;


public class ExpTree implements ExpressionService {

	private Node root;

	public ExpTree() {
		System.out.print("Introduzca la expresi�n en notaci�n infija con todos los par�ntesis y blancos: ");

		// token analyzer
		Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
		String line = inputScanner.nextLine();
		inputScanner.close();

		buildTree(line);
	}

	private void buildTree(String line) {
		// space separator among tokens
		Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
		root = new Node(lineScanner);
		lineScanner.close();
	}

	@Override
	public void preorder() {
		if(root == null)
			throw new IllegalStateException("No hay root");

		System.out.println(root.preorder());
		return;
	}

	@Override
	public void postorder() {
		if(root == null)
			throw new IllegalStateException("No hay root");

		System.out.println(root.postorder());
		return;
	}

	@Override
	public void inorder() {
		if(root == null)
			throw new IllegalStateException("No hay root");

		System.out.println(root.inorder());
		return;
	}

	@Override
	public double eval() {
		Map<String,	BinaryOperator<Double>> operatorMap = new HashMap<>();

		operatorMap.put("+", Double::sum);
		operatorMap.put("*", (x,y) -> x*y);
		operatorMap.put("/", (x,y) -> x/y);
		operatorMap.put("-", (x,y) -> x-y);
		operatorMap.put("^", Math::pow);

		return root.eval(operatorMap);
	}


	static final class Node {
		private String data;
		private Node left, right;

		private Scanner lineScanner;

		public Node(Scanner theLineScanner) {
			lineScanner = theLineScanner;

			Node auxi = buildExpression();
			data = auxi.data;
			left = auxi.left;
			right = auxi.right;

			if (lineScanner.hasNext())
				throw new RuntimeException("Bad expression");
		}

		private Node() {
		}

		private Node buildExpression() {
			// se hace con recrusión, 2 casos (operador, operando)
			// siempre creo nodo

			Node n = new Node();

			// si es operador (empieza con paréntesis)
			if(lineScanner.hasNext("\\(")) {
				lineScanner.next(); // salteo el paréntesis
				
				n.left = buildExpression(); // obs.: usa el mismo scanner
				
				n.data = lineScanner.next();
				if(!Utils.isOperator(n.data))
					throw new RuntimeException("Operador inválido");

				n.right = buildExpression();

				// chequeo que cierre bien el paréntesis
				if(lineScanner.hasNext("\\)")) {
					lineScanner.next();
				}
				else {
					throw new IncorrectParenthesisException("Paréntesis cierra mal");
				}

				return n;
			}

			// si es operando
			String token = lineScanner.next();

			if (!token.matches("-?\\d+(\\.\\d+)?")) {
				throw new RuntimeException("Operando inválido: " + token);
			}

			n.data = token;
			n.left = null;
			n.right = null;

			return n;
		}

		// recorridos lo hago de forma recursiva

		public String preorder() {
			StringBuilder s = new StringBuilder();

			s.append(data).append(" ");

			if(left != null)
				s.append(left.preorder());

			if(right != null)
				s.append(right.preorder());

			return s.toString();
		}

		public String postorder() {
			StringBuilder s = new StringBuilder();

			if (left != null)
				s.append(left.postorder());

			if (right != null)
				s.append(right.postorder());

			s.append(data).append(" ");

			return s.toString();
		}
		
		public String inorder() {
			StringBuilder s = new StringBuilder();

			if (left != null)
				s.append(left.inorder());

			s.append(data).append(" ");

			if (right != null)
				s.append(right.inorder());

			return s.toString();
		}

		public double eval(Map<String,	BinaryOperator<Double>> operatorMap) {

			if(right!=null & left!=null) {
				return operatorMap.get(data).apply(left.eval(operatorMap), right.eval(operatorMap));

			}

			else {
				return Double.parseDouble(data);
			}
		}

	} // end Node class

	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();

		System.out.println();

		myExp.preorder();
		myExp.postorder();
		myExp.inorder();

		System.out.println();

		System.out.println(myExp.eval());

	}

} // end ExpTree class

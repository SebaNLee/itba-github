import java.util.Scanner;

import javax.management.RuntimeErrorException;

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

	} // end Node class

	// hasta que armen los testeos
	public static void main(String[] args) {
		ExpressionService myExp = new ExpTree();

		myExp.preorder();
		myExp.postorder();
		myExp.inorder();

	}

} // end ExpTree class

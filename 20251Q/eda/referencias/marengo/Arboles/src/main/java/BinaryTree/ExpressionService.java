package BinaryTree;

public interface ExpressionService {
	
	// lanza exception si no se puede evaluar porque hay algo mal formado en la expresion
	double eval();
	
	String preorder();
	
	String inorder();
	
	String postorder();
}

package BinaryTree;

public interface BinaryTreeService<T> {

	String printByLevels();

	String preorder();

	String postorder();

	String postorder2();

	int getHeight();

	int getSize();
}
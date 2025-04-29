package core;

public interface BSTreeInterface<T extends Comparable<? super T>> extends Iterable<T> {

	void insert(T myData);

	String preOrder();

	String postOrder();

	String inOrder();

	NodeTreeInterface<T> getRoot();
	
	int getHeight();

	boolean contains(T myData);

	T getMax();

	T getMin();

	String printByLevels();

	void delete(T myData);

	enum Traversal {BYLEVELS, INORDER, POSTORDER, PREORDER}

	void setTraversal(Traversal traversal);

	int getOccurrences(T element);

	T kEsimo(int k);

	T getCommonNode(T element1, T element2);

	T getCommonNodeWithRepeated(T element1, T element2);
}
package core;


public interface BSTreeInterface<T extends Comparable<? super T>> {

	void insert(T myData);

	void preOrder();

	void postOrder();

	void inOrder();

	NodeTreeInterface<T> getRoot();
	
	int getHeight();

}

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class SortedLinkedListWithHeader<T extends Comparable<? super T>> implements SortedListService<T>{

	private Node root;

	// me creo el header
	private T max = null;
	private T min = null;
	private int size = 0;
	
	// insert resuelto todo en la clase SortedLinkedList, iterativo
	// public boolean insert1(T data) {
		
	// 	if (data == null) 
	// 		throw new IllegalArgumentException("data cannot be null");

	// 	Node prev= null;
	// 	Node current = root;

	// 	while (current!=null && current.data.compareTo(data) < 0) {
	// 		// avanzo
	// 		prev= current;
	// 		current= current.next;
	// 	}

	// 	// repetido?
	// 	if (current!=null && current.data.compareTo(data) == 0) {
	// 		System.err.println(String.format("Insertion failed. %s repeated", data));
	// 		return false;
	// 	}

	// 	Node aux= new Node(data, current);
	// 	// es el lugar para colocarlo
	// 	if (current == root) {
	// 		// el primero es un caso especial: cambia root
	// 		root= aux;
	// 	}
	// 	else {
	// 		// nodo interno
	// 		prev.next= aux;
	// 	}
		
	// 	return true;
	// }

	
	// insert resuelto todo en la clase SortedLinkedList, recursivo
	public boolean insert(T data) {
		if (data == null) 
			throw new IllegalArgumentException("data cannot be null");
		
		boolean[] rta = new boolean[1];
		root= insertRec(data, root, rta);

		if(rta[0]) {
			size++;
			
			if(max != null && data.compareTo(max) > 0) {
				max = data;
			}
			if(min != null && data.compareTo(min) > 0) {
				min = data;
			}
		}

		return rta[0];
	}
	
	public Node insertRec(T data, Node current, boolean[] rta) {
		
		// ya está en la lista
		if(current != null && current.data.compareTo(data) == 0) {
			System.err.println("Dato repetido en la lista");
			rta[0]= false;
			return current;
		}

		// si es menor, sigo recorrieno
		if(current != null && current.data.compareTo(data) < 0) {
			current.next = insertRec(data, current.next, rta);
			return current;
		}

		// es mayor, entonces encontré dónde insertar
		rta[0] = true;
		return new Node(data, current);
	}
	

	// insert resuelto delegando al nodo
	// public boolean insert3(T data) {

	// 	if (data == null)
	// 		throw new IllegalArgumentException("data cannot be null");
	
	// 	if (root == null) {
	// 		root= new Node(data, null);
	// 		return true;
	// 	}
		
	// 	boolean[] rta= new boolean[1];
	// 	root =root.insert( data,  rta); // eso se implementa en la clase Node
		
	// 	return rta[0];
	// }
	
	

	
	
	@Override
	public boolean find(T data) {
		return getPos(data) != -1;
	}
	
	
	// delete resuelto todo en la clase SortedLinkedList, iterativo
	// @Override
	// public boolean remove1(T data) {
	// 	// completar
	// 	return true;
	// }
	
	
	// delete resuelto todo en la clase SortedLinkedList, recursivo
//	@Override
	public boolean remove(T data) {
		// completar
		return true;

	}
	

	public Node removeRec(T data, Node current, boolean[] rta) {
		
		// completar
		return null;
	}


	// delete resuelto delegando al nodo
//	@Override
	// public boolean remove3(T data) {
	// 	// completar
	// 	return true;
	// }
	
	
	
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public int size() {
		return size; // !! modifico esto, manejo header
	}

	
	@Override
	public void dump() {
		Node current = root;

		while (current!=null ) {
			// avanzo
			System.out.println(current.data);
			current= current.next;
		}
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !  (other instanceof SortedLinkedListWithHeader) )
			return false;
		
		@SuppressWarnings("unchecked")
		SortedLinkedListWithHeader<T> auxi = (SortedLinkedListWithHeader<T>) other;
		
		Node current = root;
		Node currentOther= auxi.root;
		while (current!=null && currentOther != null ) {
			if (current.data.compareTo(currentOther.data) != 0)
				return false;
			
			// por ahora si, avanzo ambas
			current= current.next;
			currentOther= currentOther.next;
		}
		
		return current == null && currentOther == null;
		
	}
	
	// -1 si no lo encontro
	protected int getPos(T data) {
		Node current = root;
		int pos= 0;
		
		while (current!=null ) {
			if (current.data.compareTo(data) == 0)
				return pos;
			
			// avanzo
			current= current.next;
			pos++;
		}
		return -1;
	}
	
	@Override
	public T getMin() {
		return min; // !! modifico esto, manejo header
	}


	@Override
	public T getMax() {
		return max; // !! modifico esto, manejo header
	}




	
	
	private final class Node {
		private T data;
		private Node next;
	
		private Node(T data, Node next) {
			this.data= data;
			this.next= next;
		}
		
		// implementación para insert3()
		// private Node insert(T data, boolean[] rta) {
			
		// 	if (this.data.compareTo(data) == 0) {
		// 		System.err.printf("Insertion failed %s%n", data);
		// 		rta[0]= false;
		// 		return this;
		// 	}

		// 	if(this.data.compareTo(data) < 0) {
		// 		// soy el ultimo?
		// 		if (next==null) {
		// 			rta[0]= true;
		// 			next   = new Node(data, null);
		// 			return this;
		// 		}
		// 		// avanzo
		// 		next   = next.insert(data, rta);
		// 		return this;
		// 	}
			
		// 	// si no, estoy en parado en el lugar a insertar
		// 	rta[0]= true;
		// 	return new Node(data, this);
		// }
	}
	


	// agrego las cosas del iterador
	public Iterator<T> iterator() {
		return new SortedLinkedListIterator();
	}
	
	// este sería el Iterator a devoler, va recorriendo la lista en orden
	private class SortedLinkedListIterator implements Iterator<T> {

		private Node current;
		Stack<Node> stack = new Stack<>();	

		public SortedLinkedListIterator() {
			current = root;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No hay siguiente elemento en la lista");
			}

			T toReturn = current.data;
			stack.push(current);
			current = current.next;
			return toReturn;
		}

		// remove del Ej6
		// !! necesito un stack para ver cuál es el último que se insertó
		@Override
		public void remove() {
			if (stack.isEmpty()) {
				throw new IllegalStateException("No se puede eliminar antes de llamar a next()");
			}
		
			Node toRemove = stack.pop(); // Último nodo retornado por next()
		
			// Caso especial: eliminar root
			if (toRemove == root) {
				root = root.next;
				return;
			}
		
			// Buscar el nodo anterior a toRemove
			Node prev = root;
			while (prev != null && prev.next != toRemove) {
				prev = prev.next;
			}
		
			if (prev != null) {
				prev.next = toRemove.next;
			}
		}
	}


	
	
	public static void main(String[] args) {
		SortedLinkedListWithHeader<String> l = new SortedLinkedListWithHeader<>();


		// para testear diferentes métodos, cambiar el nombre acá en el main()
	
		System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
		System.out.println(l.size() );
		System.out.println(l.getMin() );
		System.out.println(l.getMax() );
		System.out.println();
		
		System.out.println(l.insert("hola"));
		l.dump();
		System.out.println();
		
		System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
		System.out.println();
		
		System.out.println(l.insert("tal"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("ah"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("veo"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("bio"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("tito"));
		l.dump();
		System.out.println();


		System.out.println(l.insert("hola"));
		l.dump();
		System.out.println();
		
		
		System.out.println(l.insert("aca"));
		l.dump();
		System.out.println();
		
		System.out.println(l.size() );
		System.out.println(l.getMin() );
		System.out.println(l.getMax() );
		System.out.println();


		System.out.println("///////////////////");

		// testeo Iterable
		SortedListService<String> l2 = new SortedLinkedListWithHeader<>();
		l2.insert("hola");
		l2.insert("tal");
		l2.insert("que");
		l2.insert("como");
		l2.insert("va");
		l2.insert("todo");

		Iterator<String> iterator = l2.iterator();

		while (iterator.hasNext()) {

			String s = iterator.next();

			if(s.equals("hola") || s.equals("va")) {
				iterator.remove();
			}
		}
		l2.dump();
		System.out.println();


		System.out.println("///////////////////");

	}


}

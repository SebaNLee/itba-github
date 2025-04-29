import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{

	private Node root;
	private int size;
	private Node last;

// iterativa
	@Override
	public boolean insert(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");
		
		Node prev = null;
		Node current= root;
		
		while( current != null && current.data.compareTo(data) <0    ) {
			// avanzo
			prev= current;
			current= current.next;
		}
		
		// repetido?
		if ( current!= null && current.data.compareTo(data) ==0  ) {  
			System.err.printf("Insertion failed %s%n", data);
			return false;
		}

		// insercion segura
		Node aux = new Node(data, current);
		
		// como engancho??? cambia el root???
		if (current == root)
			// cambie el primero
			root= aux;
		else  // nodo interno
			prev.next= aux;

		if (aux.next == null)
			last = aux;

		size++;
		return true;
	}

//region Otros Inserts
	// recursiva desde afuera
	//@Override
	public boolean insert2(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");
		
		boolean[] rta= new boolean[1];
 		root =insertRec( data, root,  rta);
		
		return rta[0];
	}

	private Node insertRec(T data, Node current, boolean[] rta ) {
		// repetido?
		if ( current!= null && current.data.compareTo(data) ==0  ) {  
			System.err.println(String.format("Insertion failed %s", data));
			rta[0]= false;
			return current;
		}

		if( current != null && current.data.compareTo(data) <0    ) {
			// avanzo
			current.next   = insertRec(data, current.next, rta);
			return current;
		}
		
		
		// estoy en parado en el lugar a insertar
		rta[0]= true;
		return new Node(data, current);
		
	}

	// delega en Node
	//	@Override
	public boolean insert3(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");
		
		if (root == null) {
			root= new Node(data, null);
			return true;
		}
		
		boolean[] rta= new boolean[1];
 		root =root.insert( data,  rta);
		
		return rta[0];
	}
	//endregion
	
	@Override
	public boolean find(T data) {
		return getPos(data) != -1;
	}

	@Override
	public boolean remove(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");

		Node prev = null;
		Node current = root;

		while( current != null && current.data.compareTo(data) <0) {
			// avanzo
			prev = current;
			current = current.next;
		}

		if(current != null && current.data.compareTo(data) == 0) {
			if(current == root)
				root = current.next;
			else
				prev.next = current.next;
			if(current == last)
				last = prev;
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	@Override
	public int size() {
		return size;
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
		if (!(other instanceof SortedLinkedList))
			return false;
		
		@SuppressWarnings("unchecked")
		SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;
		
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
			if (current.data.compareTo(data) < 0) //ordenada
				return -1;

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
		if(root==null)
			throw new NoSuchElementException();
		return root.data;
	}

	@Override
	public T getMax() {
		if(root==null)
			throw new NoSuchElementException();
		return last.data;
	}

	@Override
	public Iterator<T> iterator() {
		return new SortedLinkedListIterator();
	}

	private final class Node {
		private final T data;
		private Node next;
		
		private Node(T data) {
			this.data= data;
		}
		
		private Node(T data, Node next) {
			this.data= data;
			this.next= next;
		}
		
		private Node insert(T data, boolean[] rta) {
			
			if (this.data.compareTo(data) == 0) {
				System.err.printf("Insertion failed %s%n", data);
				rta[0]= false;
				return this;
			}

			if(this.data.compareTo(data) < 0) {
				// soy el ultimo?
				if (next==null) {
					rta[0]= true;
					next   = new Node(data, null);
					return this;
				}
				// avanzo
				next   = next.insert(data, rta);
				return this;
			}
			
			// estoy en parado en el lugar a insertar
			rta[0]= true;
			return new Node(data, this);
		}
	}

	private class SortedLinkedListIterator implements Iterator<T> {
		private Node current = root;
		Stack<Node> stack = new Stack<>();

		@Override
		public boolean hasNext() {
			return current!=null;
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			T toReturn = current.data;
			stack.push(current);
			current = current.next;
			return toReturn;
		}

		@Override
		public void remove() {
			if(stack.isEmpty())
				throw new IllegalStateException();
			Node prev = stack.pop();
			if(prev == root) //
				root=current;
			else if(prev == last){
				last = stack.pop();
				last.next = null;
			}
			else{
				prev = stack.pop(); //necesito el anterior del anterior de current
				prev.next = current; //uno el anterior del anterior con el current
				stack = new Stack<>();
			}
		}
	}
}

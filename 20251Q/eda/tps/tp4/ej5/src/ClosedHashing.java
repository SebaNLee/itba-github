
import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
	final private int initialLookupSize = 10;

	final private double threshold = 0.75;
	private int size = 0;

	// est�tica. No crece. Espacio suficiente...
	@SuppressWarnings({ "unchecked" })
	private Slot<K, V>[] Lookup = (Slot<K, V>[]) new Slot[initialLookupSize];

	private Function<? super K, Integer> prehash;

	public ClosedHashing(Function<? super K, Integer> mappingFn) {
		if (mappingFn == null)
			throw new RuntimeException("fn not provided");

		prehash = mappingFn;
	}

	// ajuste al tama�o de la tabla;
	private int hash(K key) {
		if (key == null)
			throw new IllegalArgumentException("key cannot be null");

		return prehash.apply(key) % Lookup.length;
	}

	public void insertOrUpdate(K key, V data) {
		if (key == null || data == null) {
			String msg = String.format("inserting or updating (%s,%s). ", key, data);
			if (key == null)
				msg += "Key cannot be null. ";

			if (data == null)
				msg += "Data cannot be null.";

			throw new IllegalArgumentException(msg);
		}

		// si superó threshold, rehash
		if((double) (size + 1) / Lookup.length >= threshold) {
			rehash();
		}

		int index = hash(key);
		int indexLogico = -1;
		int indexFinal = (index + Lookup.length - 1) % Lookup.length;

		while(Lookup[index]!=null && !Lookup[index].key.equals(key) && index!=indexFinal) {
			if(indexLogico==-1 && Lookup[index].bajaLogica)
				indexLogico = index;
			index++;
			index = index % Lookup.length;
		}

		if(indexLogico!=-1 && (Lookup[index]==null || !Lookup[index].key.equals(key)))
			Lookup[indexLogico] = new Slot<>(key,data);
		else{
			if(Lookup[index]!=null)
				size--;
			Lookup[index] = new Slot<>(key, data);
		}
		size++;
	}

	@SuppressWarnings("unchecked") // porque no le gusta el casteo
	private void rehash() {
		
		Slot<K, V>[] oldLookup = Lookup;
		Lookup = (Slot<K,V>[]) new Slot[oldLookup.length*2];
		
		for(Slot<K, V> slot : oldLookup) {
			// no copio las bajas lógicas
			if(slot != null && !slot.bajaLogica) {
				insertOrUpdate(slot.key, slot.value);
			}
		}
	}




	// find or get
	public V find(K key) {
		if (key == null)
			return null;

		Slot<K, V> entry = Lookup[hash(key)];
		if (entry == null)
			return null;

		return entry.value;
	}

	public boolean remove(K key) {
		if (key == null)
			return false;

		// lo encontre?
		if (Lookup[hash(key)] == null)
			return false;

		Lookup[hash(key)] = null;
		return true;
	}

	public void dump() {
		for (int rec = 0; rec < Lookup.length; rec++) {
			if (Lookup[rec] == null)
				System.out.println(String.format("slot %d is empty", rec));
			else
				System.out.println(String.format("slot %d contains %s", rec, Lookup[rec]));
		}
	}

	public int size() {
		// todavia no esta implementado
		return 0;
	}

	static private final class Slot<K, V> {
		private final K key;
		private V value;
		private boolean bajaLogica = false;

		private Slot(K theKey, V theValue) {
			key = theKey;
			value = theValue;
		}

		public String toString() {
			return String.format("(key=%s, value=%s)", key, value);
		}
	}

	public static void main(String[] args) {
		ClosedHashing<Integer, String> myHash = new ClosedHashing<>(f -> f);
		myHash.insertOrUpdate(55, "Ana");
		myHash.insertOrUpdate(44, "Juan");
		myHash.insertOrUpdate(18, "Paula");
		myHash.insertOrUpdate(19, "Lucas");
		myHash.insertOrUpdate(21, "Sol");
		myHash.insertOrUpdate(22, "Sol");
		myHash.insertOrUpdate(23, "Sol");
		myHash.insertOrUpdate(24, "Sol");
		myHash.remove(24);
		myHash.remove(25);
		myHash.insertOrUpdate(25, "Sol");
		myHash.dump();

	}

	/*
	 * public static void main(String[] args) {
	 * ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
	 * myHash.insertOrUpdate(55, "Ana");
	 * myHash.insertOrUpdate(29, "Victor");
	 * myHash.insertOrUpdate(25, "Tomas");
	 * myHash.insertOrUpdate(19, "Lucas");
	 * myHash.insertOrUpdate(21, "Sol");
	 * myHash.dump();
	 * }
	 */
}
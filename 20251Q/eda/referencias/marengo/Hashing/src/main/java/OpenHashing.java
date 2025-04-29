import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class OpenHashing<K,V> implements IndexParametricService<K,V> {
    final private int initialLookupSize= 10;
    final private double threshold = 0.75;
    private int size = 0;

    @SuppressWarnings({"unchecked"})
    private List<Slot<K,V>>[] lookup = (LinkedList<Slot<K,V>>[]) Array.newInstance(LinkedList.class, initialLookupSize);

    private Function<? super K, Integer> prehash;

    public OpenHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");

        prehash= mappingFn;
    }

    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");

        return prehash.apply(key) % lookup.length;
    }

    @Override
    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg = String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";
            if (data==null)
                msg+= "Data cannot be null.";

            throw new IllegalArgumentException(msg);
        }
        int index;
        Slot<K,V> slot = new Slot<>(key,data);

        if(lookup[hash(key)]==null)
            lookup[hash(key)] = new LinkedList<>();

        if((index = lookup[hash(key)].indexOf(slot))>=0)
            lookup[hash(key)].set(index, slot);
        else {
            lookup[hash(key)].add(slot);
            size++;
            if((double)size/lookup.length >= threshold)
                rehash();
        }
    }

    @SuppressWarnings({"unchecked"})
    private void rehash() {
        List<Slot<K,V>>[] aux = lookup;
        lookup = (LinkedList<Slot<K,V>>[]) Array.newInstance(LinkedList.class, lookup.length*2);
        size = 0;
        for (List<Slot<K, V>> list : aux) {
            if(list!=null) {
                for(Slot<K,V> kvSlot : list)
                    insertOrUpdate(kvSlot.key, kvSlot.value);
            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null || lookup[hash(key)]==null)
            return null;

        int index = lookup[hash(key)].indexOf(new Slot<K,V>(key));
        return index>=0 ? lookup[hash(key)].get(index).value : null;
    }

    @Override
    public boolean remove(K key) {
        if(key==null || lookup[hash(key)]==null)
            return false;

        boolean toReturn = lookup[hash(key)].remove(new Slot<K,V>(key));
        if(lookup[hash(key)].size()==0)
            lookup[hash(key)] = null;
        return toReturn;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void dump() {
        for(List<Slot<K,V>> list : lookup)
            if(list!=null) {
                System.out.println(list);
            }
    }

    static private final class Slot<K, V>	{
        private final K key;
        private final V value;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }

        private Slot(K theKey) {
            this(theKey, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Slot)) return false;
            Slot<?, ?> slot = (Slot<?, ?>) o;
            return key.equals(slot.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }
}

import java.util.List;

public interface SimpleList<T> extends List<T> {

    <E> E reduce(E initialValue, Reducer<T, E> reducer);
}

// !!! se decalara genérico E solo para el método
// !! solo tiene alcance en reduce()
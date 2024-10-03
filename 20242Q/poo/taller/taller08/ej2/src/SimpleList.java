import java.util.List;

public interface SimpleList<E> extends List<E> {

    abstract public <T> SimpleList<T> map(CustomOperator<E, T> cop);


}


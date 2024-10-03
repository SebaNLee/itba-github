import java.util.List;

public interface SimpleList<E> extends List<E> {

    abstract public SimpleList<E> map(CustomOperator<E> cop);


}


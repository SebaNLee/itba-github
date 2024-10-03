import java.util.ArrayList;

public class SimpleArrayList<E> extends ArrayList<E> implements SimpleList<E> {

    @Override
    public <T> SimpleList<T> map(CustomOperator<E, T> cop) {

        SimpleList<T> toReturn = new SimpleArrayList<>();

        for(E iter : this) { // !! this, pues hereda Arraylist
            toReturn.add(cop.map(iter));
        }

        return toReturn;
    }

}

// !! observar que hereda ArrayList
// es un ejercicio de hencia de colecciones
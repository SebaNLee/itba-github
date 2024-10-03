import java.util.ArrayList;

public class SimpleArrayList<E> extends ArrayList<E> implements SimpleList<E> {

    @Override
    public SimpleList<E> map(CustomOperator<E> cop) {

        SimpleList<E> toReturn = new SimpleArrayList<>();

        for(E iter : this) { // !! this, pues hereda Arraylist
            toReturn.add(cop.map(iter));
        }

        return toReturn;
    }

}

// !! observar que hereda ArrayList
// es un ejercicio de hencia de colecciones
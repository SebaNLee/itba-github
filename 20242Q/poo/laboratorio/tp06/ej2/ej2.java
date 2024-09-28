

import java.util.ArrayList;
import java.util.List;

public class ej2 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(6);
        list.add(8);
        list.add(10);
        for (Integer i : list) {
            if (i % 2 == 0) {
                list.remove(i);
            }
        }
    }

}

// excepción de tipo java.util.ConcurrentModificationException
// pues se está modificando mientras se la itera

/* entonces se debe usar el .remove() del iterador en sí con el que se recorre

    Iterator<Integer> it = list.iterator();
    while (it.hasNext()) {
        if (it.next() % 2 == 0) {
            it.remove();
        }
    }

    o se puede usar .removeIf():

    list.removeIf(integer -> integer % 2 == 0); // que recibe un Predicate (ver documentación)

 */
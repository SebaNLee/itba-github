package eda;

//Cambiar la interface para que pueda manejar un tipo de datos genéricos, no necesariamente int.
//Es decir, la interface estaría dada por:
public interface IndexParametricServiceInterface<T extends Comparable<? super T>> {
    // elements serán los valores del índice, los anteriores se descartan
    // lanza excepction si elements is null o si alguno de los elementos del
    // arreglo proporcionado son null
    void initialize(T [] elements);
    // busca una key en el índice, O(log2 N)
    boolean search(T key);
    // inserta el key en pos correcta. Crece automáticamente de a chunks.
    // si el valor proporcionado es null, ignora el pedido.
    void insert(T key);
    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    void delete(T key);
    // devuelve la cantidad de apariciones de la clave especificada.
    int occurrences(T key);
    // devuelve un nuevo arreglo ordenado con los elementos que pertenecen
    // al intervalo dado por leftkey y rightkey. Si el mismo es abierto/cerrado depende
    // de las variables leftIncluded y rightIncluded. True indica que es cerrado. El valor
    // devuelto será un arrego de length 0 si no hay elementos que satisfagan al condicion
    T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded); // imprime el contenido del índice ordenado por su key
    void sortedPrint();
    // devuelve el máximo elemento del índice o null si no hay elementos
    T getMax();
    // devuelve el mínimo elemento del índice o null si no hay elementos
    T getMin();
}
//Adaptar la clase IndexWithDuplicates y los testeos de unidad correspondientes.
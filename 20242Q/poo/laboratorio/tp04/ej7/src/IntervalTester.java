public class IntervalTester {
    public static void main(String[] args) {

    // Ejemplo de invocación con start menor que end e incremento positivo
    Interval interval = new Interval(-1, 1, 0.5);
    System.out.println(interval); // [-1.0, -0.5, 0.0, 0.5, 1.0]
    System.out.println(interval.size()); // 5
    System.out.println(interval.at(3)); // 0.5
    System.out.println(interval.indexOf(0.5)); // 3
    System.out.println(interval.includes(0.5)); // true
    System.out.println(interval.equals(new Interval(-1, 1, 0.5))); // ture
    System.out.println(interval.hashCode() == new Interval(-1, 1, 0.5).hashCode()); //true

    // Ejemplo de invocación con start mayor que end e incremento negativo
    Interval otherInterval = new Interval(1, -1, -0.5); 
    System.out.println(otherInterval); // [1.0, 0.5, 0.0, -0.5, -1.0]
    System.out.println(otherInterval.size()); // 5
    System.out.println(otherInterval.at(3)); // -0.5
    System.out.println(otherInterval.indexOf(0.5)); // 1
    System.out.println(otherInterval.includes(0.5)); // true
    
    // Ejemplo de invocación con incremento por defecto
    Interval anotherInterval = new Interval(7, 10);
    System.out.println(anotherInterval); // [7.0, 8.0, 9.0, 10.0]

    }
}

// copiado el programa de prueba de soluciones

// no es necesario agregar cláusula throws, pues son excepciones unchecked
public class ej3_2 {
    public static void main(String[] args) {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(2, 1);
        System.out.println(p1.equals(p2));        
    }
}

// falso, pues no está implementado el equals

// hecho el @Override para que de true
public class ej3_3 {
    public static void main(String[] args) {
        Point p1 = new Point(2, 1);
        Point p2 = new Point(2, 1);
        System.out.println(p1.getX().equals(p2.getX()) && p1.getY().equals(p2.getY()));     
    }
}

// error, pues los getters devuelve doubles primitivos (built-in) sin equals
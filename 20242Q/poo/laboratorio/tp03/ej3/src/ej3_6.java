public class ej3_6 {
    public static void main(String[] args) {
        Point[] points = new Point[10];
        for (Point point : points) {
            System.out.println(point.getX() + ", " + point.getY());
        }        
    }
}

// error, pues no están inicializados y apuntan a null
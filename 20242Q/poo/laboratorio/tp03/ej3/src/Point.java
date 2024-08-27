public class Point {

    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{%g, %g}".formatted(x, y);
    }


    //Override para que ande ej3_2bis
    /*

    @Override
    public boolean equals(Object o) {
        Point aux = (Point) o;
        
        if(aux.getX() == x && aux.getY() == y) {
            return true;
        }

        return false;
    }
    */
}
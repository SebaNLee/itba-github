public class Triangle extends Figure {

    Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public double area() {
        // fórmula de Herón
        double semiPerimeter = perimeter() / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - firstSide()) * (semiPerimeter - secondSide()) * (semiPerimeter - thirdSide()));
    }

    @Override
    public double perimeter() {
        return firstSide() + secondSide() + thirdSide();
    }

    private double firstSide() {
        return Math.abs(p1.distanceTo(p2));
    }

    private double secondSide() {
        return Math.abs(p2.distanceTo(p3));
    }

    private double thirdSide() {
        return Math.abs(p3.distanceTo(p1));
    }

    @Override
    public String toString() {
        return "Triángulo [ %s , %s , %s ]".formatted(p1, p2, p3);
    }
}

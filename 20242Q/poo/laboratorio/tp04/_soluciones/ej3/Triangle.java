package ar.edu.itba.poo.soltp4.ej3;

public class Triangle extends Figure {

    protected final Point firstPoint, secondPoint, thirdPoint;

    public Triangle(Point firstPoint, Point secondPoint, Point thirdPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.thirdPoint = thirdPoint;
    }

    @Override
    public double area() {
        /**
         * Como desconocemos la base y la altura del triángulo
         * usamos la fórmula de Heron que utiliza el semiperímetro
         */
        double semiPerimiter = perimeter() / 2;
        return Math.sqrt(semiPerimiter * (semiPerimiter - firstSide())
                * (semiPerimiter - secondSide()) * (semiPerimiter - thirdSide()));
    }

    @Override
    public double perimeter() {
        return firstSide() + secondSide() + thirdSide();
    }

    private double firstSide() {
        return firstPoint.distanceTo(secondPoint);
    }

    private double secondSide() {
        return secondPoint.distanceTo(thirdPoint);
    }

    private double thirdSide() {
        return thirdPoint.distanceTo(firstPoint);
    }

    @Override
    public String toString() {
        return "Triángulo [ %s , %s , %s ]".formatted(firstPoint, secondPoint, thirdPoint);
    }

}

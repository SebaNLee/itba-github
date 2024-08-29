package ar.edu.itba.poo.soltp4.ej3;

public class Rectangle extends Figure {

    protected final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public double base() {
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    public double height() {
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    @Override
    public double area() {
        return base() * height();
    }

    @Override
    public double perimeter() {
        return (base() + height()) * 2;
    }

    @Override
    public String toString() {
        return "Rectángulo [ %s , %s ]".formatted(topLeft, bottomRight);
    }

}


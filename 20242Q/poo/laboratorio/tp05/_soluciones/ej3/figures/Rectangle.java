package ar.edu.itba.poo.soltp5.ej3.figures;

public class Rectangle<P extends Point> extends Figure {

    protected final P topLeft, bottomRight;

    public Rectangle(P topLeft, P bottomRight) {
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


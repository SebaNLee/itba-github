public class Rectangle extends Figure {

    Point topLeft;
    Point topRight;

    public Rectangle(Point tl, Point tr) {
        topLeft = tl;
        topRight = tr;
    }

    public double base() {
        return Math.abs(topRight.getX() - topLeft.getX());
    }

    public double height() {
        return Math.abs(topRight.getY() - topLeft.getY());
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
        return "Rectángulo [ %s , %s ]".formatted(topLeft, topRight);
    }
}

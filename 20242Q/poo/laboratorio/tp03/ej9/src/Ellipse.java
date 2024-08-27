public class Ellipse extends Figure{

    Point centerPoint;
    // los defino como radio, pero puede que esté mal por cómo se calcula area y perim de elipse
    double mayorRadius;
    double minorRadius;

    public Ellipse(Point cp, double mayr, double minr) {
        centerPoint = cp;
        mayorRadius = mayr;
        minorRadius = minr;
    }

    @Override
    public double area() {
        return Math.PI / 4 * mayorRadius * minorRadius;
    }

    @Override
    public double perimeter() {
        return Math.PI / 2 * (mayorRadius + minorRadius);
    }

    @Override
    public String toString() {
        return "Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]".formatted(centerPoint, mayorRadius, minorRadius);
    }
}

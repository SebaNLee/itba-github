public class Circle extends Ellipse {

    public Circle(Point cp, double radius) {
        super(cp, 2 * radius, 2 * radius);
    }

    @Override
    public String toString() {
        return "Círculo [Centro: %s , Radio: %.2f}]".formatted(centerPoint, mayorRadius / 2);
    }
}

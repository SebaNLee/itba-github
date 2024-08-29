package ar.edu.itba.poo.soltp4.ej3;

public class Circle extends Ellipse {

    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2 * radius, 2 * radius);
    }

    @Override
    public String toString() {
        return "Círculo [Centro: %s , Radio: %.2f}]".formatted(centerPoint, sMayorAxis / 2);
    }

}

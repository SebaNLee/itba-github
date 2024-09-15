package ar.edu.itba.poo.soltp5.ej3.figures;

public class Circle<P extends Point> extends Ellipse<P> {

    public Circle(P centerPoint, double radius) {
        super(centerPoint, 2 * radius, 2 * radius);
    }

    @Override
    public String toString() {
        return "Círculo [Centro: %s , Radio: %.2f}]".formatted(centerPoint, sMayorAxis / 2);
    }

}

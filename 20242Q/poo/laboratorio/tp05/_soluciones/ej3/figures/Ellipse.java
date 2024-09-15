package ar.edu.itba.poo.soltp5.ej3.figures;

public class Ellipse<P extends Point> extends Figure {

    protected final P centerPoint;
    protected final double sMayorAxis, sMinorAxis;

    public Ellipse(P centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public double area() {
        return Math.PI / 4 * sMayorAxis * sMinorAxis;
    }

    @Override
    public double perimeter() {
        return Math.PI / 2 * (sMayorAxis + sMinorAxis);
    }

    @Override
    public String toString() {
        return "Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]".formatted(centerPoint, sMayorAxis, sMinorAxis);
    }

}

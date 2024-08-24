package ar.edu.itba.poo.soltp3.ej3;

import java.util.Objects;

public class Point {

    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{%g,%g}".formatted(x, y);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Point point &&
                Double.compare(x, point.x) == 0 &&
                Double.compare(y, point.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

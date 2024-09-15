package ar.edu.itba.poo.soltp5.ej3.figures;

public class MovableRectangle extends Rectangle<MovablePoint> implements MovableFigure {

    public MovableRectangle(MovablePoint topLeft, MovablePoint bottomRight) {
        super(topLeft, bottomRight);
    }

    @Override
    public MovablePoint[] getPoints() {
        return new MovablePoint[]{topLeft, bottomRight};
    }

}

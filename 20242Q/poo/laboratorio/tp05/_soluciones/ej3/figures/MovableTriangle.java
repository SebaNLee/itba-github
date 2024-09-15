package ar.edu.itba.poo.soltp5.ej3.figures;

public class MovableTriangle extends Triangle<MovablePoint> implements MovableFigure {

    public MovableTriangle(MovablePoint firstPoint, MovablePoint secondPoint, MovablePoint thirdPoint) {
        super(firstPoint, secondPoint, thirdPoint);
    }

    @Override
    public MovablePoint[] getPoints() {
        return new MovablePoint[]{firstPoint, secondPoint, thirdPoint};
    }

}


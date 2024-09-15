package ar.edu.itba.poo.soltp5.ej3.figures;

public class MovablePoint extends Point implements Movable {

    public MovablePoint(double x, double y) {
        super(x, y);
    }

    @Override
    public void moveNorth(double delta) {
        y += delta;
    }

    @Override
    public void moveSouth(double delta) {
        y -= delta;
    }

    @Override
    public void moveWest(double delta) {
        x -= delta;
    }

    @Override
    public void moveEast(double delta) {
        x += delta;
    }

}

package ar.edu.itba.poo.soltp4.ej4;

public interface MovableFigure extends Movable {

    MovablePoint[] getPoints();

    @Override
    default void moveNorth(double delta) {
        for(MovablePoint movablePoint : getPoints()) {
            movablePoint.moveNorth(delta);
        }
    }

    @Override
    default void moveSouth(double delta) {
        for(MovablePoint movablePoint : getPoints()) {
            movablePoint.moveSouth(delta);
        }
    }

    @Override
    default void moveWest(double delta) {
        for(MovablePoint movablePoint : getPoints()) {
            movablePoint.moveWest(delta);
        }
    }

    @Override
    default void moveEast(double delta) {
        for(MovablePoint movablePoint : getPoints()) {
            movablePoint.moveEast(delta);
        }
    }

}

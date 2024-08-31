public class MovablePoint extends Point implements Movable{

    public MovablePoint(double x, double y) {
        super(x, y);
    }

    public void moveNorth(double delta) {
        y += y;
    }

    public void moveSouth(double delta) {
        y -= y;
    }

    public void moveEast(double delta) {
        x += x;
    }

    public void moveWest(double delta) {
        x -= x;
    }
}

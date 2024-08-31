public class MovableTester {
    public static void main(String[] args) {

        // copio el programa de prueba desde soluciones
        MovablePoint aPoint = new MovablePoint(1,2);
        System.out.println(aPoint);
        aPoint.moveNorth(3);
        System.out.println(aPoint);

    }
}

// para mover las figuras basta con mover todos sus puntos

// incompleto:
// ahora se debería crear subclases Movable para toda figura que implemente interfaz Movable
// no lo hago
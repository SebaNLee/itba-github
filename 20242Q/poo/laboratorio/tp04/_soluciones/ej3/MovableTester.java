package ar.edu.itba.poo.soltp4.ej3;

public class MovableTester {

    public static void main(String[] args) {
        MovablePoint aPoint = new MovablePoint(1,2);
        System.out.println(aPoint);
        aPoint.moveNorth(3);
        System.out.println(aPoint);
    }

}

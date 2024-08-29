package ar.edu.itba.poo.soltp4.ej15;

public class OperationTester {

    public static void main(String[] args) {
        double x = 4;
        double y = 2;
        for(Operation operation : BasicOperation.values()) {
            System.out.printf("%.2f %s %.2f = %.2f\n", x, operation, y, operation.apply(x,y));
        }
        for(Operation operation : ExtendedOperation.values()) {
            System.out.printf("%.2f %s %.2f = %.2f\n", x, operation, y, operation.apply(x,y));
        }
    }

}



public class OperationTester {

    public static void main(String[] args) {
        double x = 4;
        double y = 2;
        for(BasicOperation operation : BasicOperation.values()) {
            System.out.printf("%.2f %s %.2f = %.2f\n", x, operation, y, operation.apply(x,y));
        }
        for(ExtendedOperation operation : ExtendedOperation.values()) {
            System.out.printf("%.2f %s %.2f = %.2f\n", x, operation, y, operation.apply(x,y));
        }
    }

}

// !!
// BasicOp lo hago con lambda (más corto)
// ExtendedOp lo hago con declaración normal


/* salida esperada:
    4,00 + 2,00 = 6,00
    4,00 - 2,00 = 2,00
    4,00 * 2,00 = 8,00
    4,00 / 2,00 = 2,00
    4,00 ^ 2,00 = 16,00
    4,00 % 2,00 = 0,00
 */


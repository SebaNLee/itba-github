package ar.edu.itba.poo.soltp4.ej11;

public class PolynomialTester {

    public static void main(String[] args) {
        Polynomial fourthGradePol = null;
        try {
            fourthGradePol = new Polynomial(4);
        } catch (InvalidGradeException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            fourthGradePol.set(2, 3.1);
            fourthGradePol.set(3, 2);
        } catch (InvalidIndexException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(fourthGradePol.eval(2)); // 28.4
        try {
            System.out.println(new Polynomial(3).eval(5)); // 0
        } catch (InvalidGradeException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            new Polynomial(-4);
        } catch (InvalidGradeException ex) {
            System.out.println(ex.getMessage()); // 0
        }
        try {
            fourthGradePol.set(7, 1.5);
        } catch (InvalidIndexException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

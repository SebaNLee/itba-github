package ar.edu.itba.poo.soltp4.ej11;

public class Polynomial {

    private double[] polynomial;

    public Polynomial(int grade) throws InvalidGradeException {
        if(grade < 0) {
            throw new InvalidGradeException();
        }
        this.polynomial = new double[grade + 1];
    }

    public void set(int index, double value) throws InvalidIndexException {
        if(index < 0 || index > polynomial.length) {
            throw new InvalidIndexException();
        }
        polynomial[index] = value;
    }

    public double eval(double value) {
        double result = 0;
        for(int i = 0; i < polynomial.length; i++) {
            result += polynomial[i] * Math.pow(value,i);
        }
        return result;
    }

}

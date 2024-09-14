public class Polynomial {

    private double[] polynomial;

    public Polynomial(int grade) throws InvalidGradeException {

        if(grade < 0) {
            throw new InvalidGradeException();
        }

        polynomial = new double[grade];
    }

    public void set(int index, double value) throws InvalidIndexException {

        if(index < 0 || index > polynomial.length) {
            throw new InvalidIndexException();
        }

        polynomial[index] = value;

    }

    public double eval(double value) {
        
        double returnValue = 0;

        for(int i = 0; i < polynomial.length; i++) {
            returnValue += polynomial[i] * Math.pow(value, i);
        }

        return returnValue;
    }

}

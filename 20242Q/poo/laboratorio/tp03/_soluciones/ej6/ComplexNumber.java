package ar.edu.itba.poo.soltp3.ej6;

import java.util.Objects;

public final class ComplexNumber {

    private final double real, imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(Number real) {
        this(real.doubleValue(), 0);
    }

    public ComplexNumber plus(ComplexNumber c) {
        return new ComplexNumber(real + c.real, imaginary + c.imaginary);
    }

    public ComplexNumber plus(Number n) {
        return plus(new ComplexNumber(n));
    }

    @Override
    public String toString() {
        return "%.2f %c %.2f".formatted(real, imaginary > 0 ? '+' : '-', Math.abs(imaginary));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ComplexNumber that &&
                Double.compare(that.real, real) == 0 &&
                Double.compare(that.imaginary, imaginary) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

}

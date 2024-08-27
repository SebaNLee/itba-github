

public class ComplexNumber {
    
    private double real;
    private double imag;

    public ComplexNumber(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public ComplexNumber(Number real) {
        // llamo al constructor con la parte imag en 0
        this(real.doubleValue(), 0);
    }

    @Override
    public boolean equals(Object o) {

        if(!(o instanceof ComplexNumber)) {
            return false;
        }

        ComplexNumber temp = (ComplexNumber) o;

        return Double.compare(temp.real, real) == 0 && // !! si es mismo objecto, puedo acceder a campos privados de instancias del mismo objeto
                Double.compare(temp.imag, imag) == 0;
    }

    @Override
    public String toString() {
        return "%.2f %c %.2f".formatted(real, imag > 0 ? '+' : '-', Math.abs(imag));
    }

    // notar que plus no debe guardar valores, solo devolver una nueva instancia
    public ComplexNumber plus(ComplexNumber cn) {
        return new ComplexNumber(real + cn.real, imag + cn.imag);
    }

    // con este plus lo vuelvo usable para todo Number
    public ComplexNumber plus(Number n) {
        return plus(new ComplexNumber(n));
    }
}

// obs:
// recordar que println() llama a toString si recibe objetos
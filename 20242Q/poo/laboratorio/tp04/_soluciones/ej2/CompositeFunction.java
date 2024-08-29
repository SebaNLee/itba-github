package ar.edu.itba.poo.soltp4.ej2;

public class CompositeFunction implements Function {

    private Function f1, f2;

    public CompositeFunction(Function f1, Function f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public double evaluate(double x) {
        return f2.evaluate(f1.evaluate(x));
    }

}

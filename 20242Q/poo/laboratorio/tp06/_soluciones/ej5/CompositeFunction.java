package ar.edu.itba.poo.soltp6.ej5;

import java.util.ArrayList;
import java.util.List;

public class CompositeFunction implements Function {

    private List<Function> fn;

    public CompositeFunction() {
        fn = new ArrayList<>();
    }

    public void addFunction(Function function) {
        fn.add(function);
    }

    @Override
    public double evaluate(double x) {
        double answer = x;
        if (fn.isEmpty()) {
            throw new IllegalStateException();
        }
        for (Function f : fn) {
            answer = f.evaluate(answer);
        }
        return answer;
    }

}

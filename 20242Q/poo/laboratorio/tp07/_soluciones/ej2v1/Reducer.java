package ar.edu.itba.poo.soltp7.ej2v1;

@FunctionalInterface
public interface Reducer<ValueIn, ValueOut> {

    ValueOut reduce(ValueOut accum, ValueIn value);

}

@FunctionalInterface
public interface Reducer<ValueIn, ValueOut> {

    ValueOut reduce(ValueOut accum, ValueIn value);
}

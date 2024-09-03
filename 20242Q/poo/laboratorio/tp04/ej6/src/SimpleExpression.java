public class SimpleExpression extends Expression{

    private boolean value;

    public SimpleExpression(boolean value) {
        super();
        setValue(value);
    }

    @Override
    public boolean evaluate() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

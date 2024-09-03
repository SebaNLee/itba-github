public class NotExpression extends Expression{

    Expression expr;

    public NotExpression(Expression expr) {
        this.expr = expr;
    }

    @Override
    public boolean evaluate() {
        return !expr.evaluate();
    }
}

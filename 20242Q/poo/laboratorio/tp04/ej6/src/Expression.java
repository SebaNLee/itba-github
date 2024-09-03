public abstract class Expression {

    public abstract boolean evaluate();

    public Expression not() {
        return new NotExpression(this);
    }

    public Expression and(Expression expr) {
        return new AndExpression(this, expr);
    }

    public Expression or(Expression expr) {
        return new OrExpression(this, expr);
    }
}

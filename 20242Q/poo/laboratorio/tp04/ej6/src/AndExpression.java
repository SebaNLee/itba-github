public class AndExpression extends BinaryExpression{

    public AndExpression(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    public boolean evaluate() {
        return leftExpr.evaluate() && rightExpr.evaluate();
    }
}

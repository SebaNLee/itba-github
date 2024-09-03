public class OrExpression extends BinaryExpression{

    public OrExpression(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    public boolean evaluate() {
        return leftExpr.evaluate() || rightExpr.evaluate();
    }
}

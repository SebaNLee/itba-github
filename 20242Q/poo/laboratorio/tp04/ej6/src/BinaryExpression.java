public abstract class BinaryExpression extends Expression{
    
    protected Expression leftExpr, rightExpr;

    public BinaryExpression(Expression l, Expression r) {
        leftExpr = l;
        rightExpr = r;
    }

    public abstract boolean evaluate(); // lo pateo para las subclases
}

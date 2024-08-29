package ar.edu.itba.poo.soltp4.ej6;

public class OrExpression extends BinaryExpression {

    public OrExpression(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public boolean evaluate() {
        return leftExpression.evaluate() || rightExpression.evaluate();
    }

}

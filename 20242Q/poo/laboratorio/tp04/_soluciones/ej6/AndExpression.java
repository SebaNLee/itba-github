package ar.edu.itba.poo.soltp4.ej6;

public class AndExpression extends BinaryExpression {

    public AndExpression(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public boolean evaluate() {
        return leftExpression.evaluate() && rightExpression.evaluate();
    }

}

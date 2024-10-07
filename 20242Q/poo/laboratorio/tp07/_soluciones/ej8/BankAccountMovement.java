package ar.edu.itba.poo.soltp7.ej8;

public class BankAccountMovement {

    private final BankAccountMovementType type;
    private final double balance;

    BankAccountMovement(BankAccountMovementType type, double balance) {
        this.type = type;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "%s $%.2f".formatted(type, Math.abs(balance));
    }

}

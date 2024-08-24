package ar.edu.itba.poo.soltp3.ej7;

public abstract class BankAccount {

    private final int id;
    protected double balance;

    protected BankAccount(int id) {
        this.id = id;
    }

    public double deposit(double amount) {
        return balance += amount;
    }

    public double extract(double amount) {
        if(canExtract(amount)) {
            return balance -= amount;
        }
        return balance;
    }

    protected abstract boolean canExtract(double amount);

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Cuenta %d con saldo %.2f".formatted(id, balance);
    }

}

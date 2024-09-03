

public abstract class BankAccount {
    
    private final int id; // se agrega final, pues no se modifica
    protected double balance; // protected así no necesito to getters

    public BankAccount(int id) {
        this.id = id;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double extract(double amount) {
        
        if(canExtract(amount)) {
            balance -= amount;
        }
        else { // agrego el unchecked exception
            throw new RuntimeException("Fondos insuficientes");
        }
        return balance;
    }

    protected abstract boolean canExtract(double amount); // debe ser protected así acepta Override, no se puede con private !!

    public String toString() {
        return "Cuenta %d con saldo %.2f".formatted(id, balance);
    }
}

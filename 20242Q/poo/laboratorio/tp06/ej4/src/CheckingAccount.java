public class CheckingAccount extends BankAccount{

    private double overdraft;

    public CheckingAccount(int id, double overdraft) {
        super(id);
        this.overdraft = overdraft;
    }

    protected boolean canExtract(double amount) {
        if(balance - amount <= overdraft) {
            return true;
        }

        return false;
    }

    // esto no se usa, pero lo implemento igual
    public double getOverdraft() {
        return overdraft;
    }
}

// copiado
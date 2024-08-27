public class SavingsAccount extends BankAccount{

    public SavingsAccount(int id) {
        super(id);
    }

    @Override
    protected boolean canExtract(double amount) {
        if(balance >= amount) {
            return true;
        }

        return false;
    }
}

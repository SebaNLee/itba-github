package ar.edu.itba.poo.soltp3.ej7;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(int id) {
        super(id);
    }

    @Override
    protected boolean canExtract(double amount) {
        return balance - amount >= 0;
    }

}

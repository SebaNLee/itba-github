package ar.edu.itba.poo.soltp6.ej4;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final List<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public void removeAccount(BankAccount account) {
        accounts.remove(account);
    }

    public int accountSize() {
        return accounts.size();
    }

    public double totalAmount() {
        double totalAmount = 0;
        for(BankAccount account : accounts) {
            totalAmount += account.getBalance();
        }
        return totalAmount;
    }

}

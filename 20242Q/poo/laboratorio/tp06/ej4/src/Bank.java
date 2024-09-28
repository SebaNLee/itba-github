import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bank {

    private List<BankAccount> accounts = new ArrayList<>();

    // constructor default

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
        Iterator<BankAccount> it = accounts.iterator();
        double totalBalance = 0;

        while(it.hasNext()) {
            BankAccount temp = it.next();
            totalBalance += temp.balance;
        }

        return totalBalance;
    }
    
}


// se está agregando saldo directamente con métodos de las cuentas bancarias, entonces necesito iterar
// obs: se podía hacer con for-each directamente
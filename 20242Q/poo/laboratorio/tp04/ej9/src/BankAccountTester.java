
public class BankAccountTester {

    public static void main(String[] args) {
        CheckingAccount myCheckingAccount = new CheckingAccount(1001, 50);
        myCheckingAccount.deposit(100.0);
        System.out.println(myCheckingAccount);
        myCheckingAccount.extract(150.0);
        System.out.println(myCheckingAccount);

        SavingsAccount mySavingsAccount = new SavingsAccount(1002);
        mySavingsAccount.deposit(100.0);
        System.out.println(mySavingsAccount);
        mySavingsAccount.extract(150.0); // No se realiza por falta de fondos
        System.out.println(mySavingsAccount);
    }

}

// código copiado, agrego la excepción como me pide el ejercicio en BankAccount.java
// !! si fuese CheckedException, debería agregar throws al método y correr el código con try-catch

/*
    salida esperada:
    Cuenta 1001 con saldo 100,00
    Cuenta 1001 con saldo -50,00
    Cuenta 1002 con saldo 100,00
    Cuenta 1002 con saldo 100,00
 */

 












 /*
respondo:

a) no, es necesario declararlo manualmente con el nombre correspondiente
b) si, pues es subclase
c) no, pues es clase padre
d) sería BanckAccount al momento de ejecución
e) no se puede, pues está guardado como un BankAccount
f) !!! se arreglaría con un casteo ((CheckingAccount)myBankAccount).getOverdraft(), siendo myBankAccount un CheckingAccount guardado en BankAccount

  */

 
package ar.edu.itba.poo.soltp7.ej8;

public class PremiumAccount extends CheckingAccount {

    private final ShopDiscountsProvider provider;

    public PremiumAccount(int id, double overdraft, ShopDiscountsProvider provider) {
        super(id, overdraft);
        this.provider = provider;
    }

    public double extract(double amount, String shop) {
        return super.extract(
                new PremiumBankAccountMovement(BankAccountMovementType.EXTRACTION,
                        amount * (1 - provider.getDiscount(shop)), shop));
    }

}

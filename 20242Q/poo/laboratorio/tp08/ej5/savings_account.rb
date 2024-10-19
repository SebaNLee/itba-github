require_relative 'bank_account'

class SavingsAccount < BankAccount

  def initialize(id)
    initialize_bank_account(id)
  end

  def extract?(amount)
    @balance - amount >= 0
  end

  private :extract?
end
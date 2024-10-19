require_relative 'bank_account'

class CheckingAccount < BankAccount

  def initialize(id, overdraft)
    initialize_bank_account(id)
    @overdraft = overdraft
  end

  def extract?(amount)
    @balance + @overdraft - amount >= 0
  end

  private :extract?
end
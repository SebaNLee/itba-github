# clase abstracta

class BankAccount
  def initialize
    raise "No se puede instanciar BankAccount"
  end

  def initialize_bank_account(id)
    @id = id
    @balance = 0
  end

  def deposit(amount)
    @balance += amount
  end

  def extract(amount)
    raise "No cuenta con los fondos necesarios" unless extract?(amount) # !!
    @balance -= amount
  end

  # devuelve true si se puede extraer
  def extract?(_amount) # !! se usa _ al principio si no se va a usar
    raise "sobreescribir este método"
  end

  def to_s
    "Cuenta #{@id} con saldo #{@balance}"
  end

  private :initialize_bank_account, :extract?
end
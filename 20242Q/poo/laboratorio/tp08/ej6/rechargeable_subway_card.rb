require_relative 'subway_card'

class RechargeableSubwayCard < SubwayCard
  def initialize(subway_central)
    @subway_central = subway_central
    @balance = 0
  end
  
  def recharge(amount)
    raise 'Invalid amount' unless amount.is_a?(Numeric) && amount.positive?
    @balance += amount
  end

  private def ride_cost
    @subway_central.ride_cost
  end

  private def ride?
    (@balance - ride_cost).positive?
  end

  def ride
    raise 'Not enough funds' unless ride?
    @balance -= ride_cost
  end

  def to_s
    "Rechargable #{super} [Balance: $#{@balance}]"
  end
end

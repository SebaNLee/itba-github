require_relative 'call'

class FriendlyCall < Call

  def initialize(from, to, duration, friend_cell_phone_bill)
    super(from, to, duration)
    @friend_cell_phone_bill = friend_cell_phone_bill
  end

  def cost
    super * @friend_cell_phone_bill.friend_cost_per_second
  end
end

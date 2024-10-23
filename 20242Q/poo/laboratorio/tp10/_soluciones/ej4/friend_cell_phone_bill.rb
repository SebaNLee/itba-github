require_relative 'cell_phone_bill'

class FriendCellPhoneBill < CellPhoneBill
  DEFAULT_FRIEND_COST_PER_SECOND = 0

  def initialize(number, limit)
    super(number)
    @friends = []
    @limit = limit
    @@friend_cost_per_second = DEFAULT_FRIEND_COST_PER_SECOND
  end

  def add_friend(number)
    raise 'Friend already exists' if @friends.include? number
    raise 'Too many friends' if @friends.length == @limit
    @friends.push(number)
  end

  def remove_friend(number)
    raise 'Friend not found' unless @friends.include? number
    @friends.delete(number)
  end

  def change_cost(cost)
    @@friend_cost_per_second = cost
  end

  def process_bill
    @calls.map { |c| @friends.include?(c.to) ? @@friend_cost_per_second * c.cost : c.cost }.reduce(0, :+)
  end
end

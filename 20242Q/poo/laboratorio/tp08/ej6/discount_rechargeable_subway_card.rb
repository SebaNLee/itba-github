require_relative 'rechargeable_subway_card'

class DiscountRechargeableSubwayCard < RechargeableSubwayCard
  def initialize(subway_central, discount_percentage)
    super(subway_central)
    raise 'Invalid discount' unless discount_percentage.is_a?(Numeric) && discount_percentage >= 0 && discount_percentage <= 1
    @discount_percentage = discount_percentage
  end

  private def ride_cost
    super * (1 - @discount_percentage)
  end
end
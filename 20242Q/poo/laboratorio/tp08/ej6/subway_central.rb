
class SubwayCentral
  attr_reader :ride_cost

  def initialize(ride_cost)
    self.ride_cost=(ride_cost) # !! reutilizo el setter con validación
  end

  def ride_cost=(ride_cost) # !! el = lo vuelve un setter
    raise 'Invalid ride_cost' unless ride_cost.is_a?(Numeric) && ride_cost.positive?
    @ride_cost = ride_cost
  end

  def to_s
    "Subway Central [$#{@ride_cost}]"
  end
end
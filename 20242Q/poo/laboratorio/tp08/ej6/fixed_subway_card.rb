require_relative 'subway_card'

class FixedSubwayCard < SubwayCard
  def initialize(subway_central, rides)
    @subway_central = subway_central
    raise 'Invalid rides' unless rides.is_a?(Integer) && rides.positive?
    @rides = rides
  end

  private def ride?
    @rides.positive?
  end

  def ride
    raise 'No rides available' unless self.ride? # ??
    @rides -= 1
  end

  def to_s
    "Fixed #{super} [Rides: #{@rides}]"
  end
end

require_relative 'subway_central'

class SubwayCard
  @subway_central

  def initialize
    raise 'Can\'t use this card'
  end

  private def ride?
    raise 'Can\'t use this card'
  end

  def ride
    raise 'Can\'t use this card'
  end

  def to_s
    "Subway Card [Central: #{@subway_central}]"
  end
end

require_relative 'subway_central'

class SubwayCard
  @subway_central # !! declaro que debe tener una central

  def initialize
    raise 'No se puede instanciar esto'
  end

  private def ride?
    raise 'Implementar'
  end

  def ride
    raise 'Implementar'
  end

  def to_s
    "Subway Card [Central: #{@subway_central}]"
  end
end
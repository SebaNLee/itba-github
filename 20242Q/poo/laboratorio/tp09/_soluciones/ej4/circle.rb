require_relative 'ellipse'

class Circle < Ellipse
  def initialize(center_point, radius)
    super(center_point, 2 * radius, 2 * radius)
  end

  def to_s
    "Círculo [Centro: #{@center_point} , Radio: #{@smayor_axis / 2}]"
  end
end

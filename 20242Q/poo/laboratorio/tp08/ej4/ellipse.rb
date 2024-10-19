require_relative 'figure'

class Ellipse < Figure
  def initialize(center, mayor_axis, minor_axis)
    @center = center
    @mayor_axis = mayor_axis
    @minor_axis = minor_axis
  end

  def area
    Math::PI / 4 * @mayor_axis * @minor_axis
  end

  def perimeter
    Math::PI / 2 * (@mayor_axis * @minor_axis)
  end

  def to_s
    "Elipse [Centro: #{@center}, DMayor: #{@mayor_axis}, DMenor: #{@minor_axis}]"
  end
end

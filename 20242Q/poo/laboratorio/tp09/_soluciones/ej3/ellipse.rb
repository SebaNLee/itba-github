require_relative 'figure'

class Ellipse < Figure
  attr_reader :center_point, :smayor_axis, :sminor_axis

  def initialize(center_point, smayor_axis, sminor_axis)
    @center_point = center_point
    @smayor_axis = smayor_axis
    @sminor_axis = sminor_axis
  end

  def area
    Math::PI / 4 * @smayor_axis * @sminor_axis
  end

  def perimeter
    Math::PI / 2 * (@smayor_axis + @sminor_axis)
  end

  def to_s
    "Elipse [Centro: #{@center_point}, DMayor: #{@smayor_axis}, DMenor: #{@sminor_axis}]"
  end

  def ==(other)
    return false unless other.is_a?(Ellipse)
    @center_point == other.center_point && @smayor_axis == other.smayor_axis && @sminor_axis == other.sminor_axis
  end

  def hash
    [@center_point, @smayor_axis, @sminor_axis].hash
  end
end

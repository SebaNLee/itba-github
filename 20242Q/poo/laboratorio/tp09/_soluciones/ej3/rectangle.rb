require_relative 'figure'

class Rectangle < Figure
  attr_reader :top_left_point, :bottom_right_point

  def initialize(top_left_point, bottom_right_point)
    @top_left_point = top_left_point
    @bottom_right_point = bottom_right_point
  end

  def base
    (@top_left_point.x - @bottom_right_point.x).abs
  end

  def height
    (@top_left_point.y - @bottom_right_point.y).abs
  end

  def area
    base * height
  end

  def perimeter
    (base + height) * 2
  end

  def to_s
    "Rectángulo [ #{@top_left_point} , #{@bottom_right_point} ]"
  end

  def ==(other)
    return false unless other.is_a?(Rectangle)
    @top_left_point == other.top_left_point && @bottom_right_point == other.bottom_right_point
  end

  def hash
    [@top_left_point, @bottom_right_point].hash
  end
end

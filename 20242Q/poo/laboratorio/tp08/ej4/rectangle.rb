require_relative 'figure'

class Rectangle < Figure
  def initialize(top_left, bottom_right)
    @top_left = top_left
    @bottom_right = bottom_right
  end

  def area
    base * height
  end

  def perimeter
    (base + height) * 2
  end

  def to_s
    "Rectángulo [ #{@top_left} , #{@bottom_right} ]"
  end

  private

  def base
    (@top_left.x - @bottom_right.x).abs
  end

  def height
    (@top_left.y - @bottom_right.y).abs
  end
end

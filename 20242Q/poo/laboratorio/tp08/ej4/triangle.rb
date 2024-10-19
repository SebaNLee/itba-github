require_relative 'figure'

class Triangle < Figure
  def initialize(first, second, third)
    @first = first
    @second = second
    @third = third
  end

  def perimeter
    first_side + second_side + third_side
  end

  def area
    semiperimeter = perimeter / 2
    Math.sqrt(semiperimeter * (semiperimeter - first_side) * (semiperimeter - second_side) * (semiperimeter - third_side))
  end

  def to_s
    "Triángulo [ #{@first} , #{@second} , #{@third} ]"
  end

  private

  def first_side
    @first.distance(@second)
  end

  def second_side
    @second.distance(@third)
  end

  def third_side
    @third.distance(@first)
  end
end

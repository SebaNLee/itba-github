require_relative 'figure'

class Circle < Ellipse
  def initialize(center, radius)
    super(center, 2 * radius, 2 * radius)
  end

  def to_s
    "Círculo [Centro: #{@center} , Radio: #{@radius}]"
  end
end

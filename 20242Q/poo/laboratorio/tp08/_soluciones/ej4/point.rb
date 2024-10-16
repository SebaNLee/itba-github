class Point
  attr_reader :x, :y

  def initialize(x, y)
    @x, @y = x, y
  end

  def to_s
    "{#{@x}, #{@y}}"
  end

  def distance(point)
    ((point.x - @x)**2 + (point.y - @y)**2)**0.5
  end
end

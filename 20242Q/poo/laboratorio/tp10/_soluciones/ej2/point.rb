class Point
  attr_reader :x, :y

  def initialize(x, y)
    @x = x
    @y = y
  end

  def to_s
    "{#{@x}, #{@y}}"
  end

  def ==(other)
    return false unless other.is_a?(Point)
    @x == other.x && @y == other.y
  end

  def eql?(other)
    self.==(other)
  end

  def hash
    [@x, @y].hash
  end
end

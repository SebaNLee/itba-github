
class Point
  def initialize(x, y)
    @x = x
    @y = y
  end

  def distance(point)
    ((point.x - @x) ** 2 + (point.y - @y) ** 2) ** 0.5
  end
  
  def to_s
    "{#{@x}, #{@y}}"
  end

  #agrego
  def ==(other)
    # si no es point, entonces no son equivalentes
    return false unless other.is_a?(Point)
    
    # si es point, comparar para equivalencia
    @x == other.x && @y == other.y
  end

  protected # solo entre esta clase

  attr_reader :x, :y

end
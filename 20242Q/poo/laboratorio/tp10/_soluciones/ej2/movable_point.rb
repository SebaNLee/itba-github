require_relative 'point'
require_relative 'movable'

class MovablePoint < Point
  include Movable

  def move_up(delta)
    @y += delta
  end

  def move_down(delta)
    @y -= delta
  end

  def move_left(delta)
    @x -= delta
  end

  def move_right(delta)
    @x += delta
  end
end

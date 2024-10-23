require_relative 'circle'
require_relative 'movable_figure'

class MovableCircle < Circle
  include MovableFigure

  def points
    [@center_point]
  end
end

require_relative 'ellipse'
require_relative 'movable_figure'

class MovableEllipse < Ellipse
  include MovableFigure

  def points
    [@center_point]
  end
end

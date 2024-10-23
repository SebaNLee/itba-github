require_relative 'rectangle'
require_relative 'movable_figure'

class MovableRectangle < Rectangle
  include MovableFigure

  def points
    [@top_left_point, @bottom_right_point]
  end
end

require_relative 'triangle'
require_relative 'movable_figure'

class MovableTriangle < Triangle
  include MovableFigure

  def points
    [@first_point, @second_point, @third_point]
  end
end

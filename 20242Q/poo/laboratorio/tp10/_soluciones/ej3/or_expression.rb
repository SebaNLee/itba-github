require_relative 'expression'

class OrExpression
  include Expression

  def initialize(left, right)
    @left = left
    @right = right
  end

  def evaluate
    @left.evaluate || @right.evaluate
  end
end

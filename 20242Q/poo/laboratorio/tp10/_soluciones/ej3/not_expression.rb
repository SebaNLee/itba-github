require_relative 'simple_expression'

class NotExpression < SimpleExpression
  def evaluate
    !super.evaluate
  end
end

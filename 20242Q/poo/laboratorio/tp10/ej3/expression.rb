module Expression
  def evaluate
    raise 'Not implemented'
  end

  # !! todas deben tener métodos que invoquen a constructores
  # entonces, agrego:

  def not
    require_relative 'not_expression'
    NotExpression.new(self)
  end

  def and(expression)
    require_relative 'and_expression'
    AndExpression.new(self, expression)
  end

  def or(expression)
    require_relative 'or_expression'
    OrExpression.new(self, expression)
  end
end


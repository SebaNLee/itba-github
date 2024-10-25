
class NotExpression < SimpleExpression

  include Expression

  def evaluate
    !super.evaluate # no se puede hacer !@value, pues no considera que sea operación binaria
  end

end
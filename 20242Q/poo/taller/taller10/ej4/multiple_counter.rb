require_relative 'counter'

class MultipleCounter < Counter
  attr_accessor :count

  def initialize(step)
    super() # !!
    @step = step
  end

  def increment
    @count += @step
  end

  def decrement
    @count -= @step
  end
end

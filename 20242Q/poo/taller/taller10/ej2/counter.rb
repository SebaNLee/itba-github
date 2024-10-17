# frozen_string_literal: true

class Counter

  attr_accessor :count # con esto puedo acceder y modificar 

  def initialize
    @count = 0
  end

  def increment
    @count += 1
  end

  def decrement
    @count -= 1
  end
end
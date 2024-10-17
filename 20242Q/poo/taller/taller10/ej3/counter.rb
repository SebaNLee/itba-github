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

  # este sería lo que se agrega, el relativo a toString en Java
  def to_s
    "#{@count}" # o count.to_s
  end
end

require_relative 'stack'

class AccessStack < Stack
  attr_reader :pop_accesses

  def initialize
    super
    @pop_accesses = 0
  end

  def pop
    to_return = super
    @pop_accesses += 1
    to_return
  end

  def push_accesses
    @element.size + @pop_accesses
  end
end
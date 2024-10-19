
class Stack
  def initialize
    @elements = [] # !! Array vacía
  end

  def push(element)
    @elements.push(element)
  end

  def peek
    empty_check
    @elements.last
  end

  def pop
    self.empty_check
    @elements.pop
  end

  def empty?
    @elements.empty?
  end

  private def empty_check
    raise 'Stack is empty' if empty?
  end
end
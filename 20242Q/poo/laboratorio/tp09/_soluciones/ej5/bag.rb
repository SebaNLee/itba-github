class Bag
  def initialize
    @elements = Hash.new { 0 }
  end

  def add(element)
    @elements[element] += 1
  end

  def size
    @elements.length
  end

  def count(element)
    @elements[element]
  end

  def delete(element)
    @elements[element] -= 1 if @elements.key? element
    @elements.delete(element) if @elements[element].zero?
    @elements[element]
  end

  def to_s
    @elements.to_s
  end
end

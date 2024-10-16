class Figure
  def initialize
    raise 'No se puede instanciar una Figura'
  end

  def perimeter
    raise 'Necesita implementar el método perimeter'
  end

  def area
    raise 'Necesita implementar el método area'
  end

  def inspect
    to_s
  end

  def eql?(other)
    self.==(other)
  end

  def <=>(other)
    area <=> other.area # Orden Ascendente
  end
end

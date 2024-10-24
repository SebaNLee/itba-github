require_relative 'product'

class Item

  attr_reader :product, :cant

  def initialize(product, cant)
    @product = product
    @cant = cant
  end

  def to_s
    format("%s %d $%f", @product.name, @cant, @product.price)
  end

  def item_total # see podía hacer la def de 1 línea
    @cant * @product.price
  end

  # le agrego esto para el ej2
  def ==(other)
    @product.name == other.get_product_name
  end

  # 
  def add_cant(add_cant)
    @cant += add_cant
  end

  #
  def get_product_name
    @product.name
  end

end
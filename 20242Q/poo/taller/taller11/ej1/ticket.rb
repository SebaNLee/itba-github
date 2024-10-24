require_relative 'item'

class Ticket

  @@id = 1000

  def initialize
    @ticket_id = @@id
    @@id += 1
    @collection = Array.new(0)
  end

  def add(product, cant) # se podía hacer @collection << Item.new...
    @collection.append(Item.new(product, cant))
  end

  # se debía retornar un String, y no imprimir
  # lo ideal era crear un String s y se le iba haciendo += ... \n, línea por línea
  def to_s
    puts format("# TICKET Nº %d", @ticket_id)
    puts "# ####################"
    for item in @collection
      puts format("# %s %d $%.2f", item.product.name, item.cant, item.product.price)
    end
    puts "# ####################"
    puts format("TOTAL $%.2f", ticket_total)
  end

  # !! era mejor, @collection.map { |item| item.total }.reduce { |accum, e| accuum + e}
  # todavía mejor, se puede mandarle un símbolo de operación al reduce: .reduce(:+)
  def ticket_total
    to_return = 0
    for item in @collection # se podía, y era mejor, hacerlo con each
      to_return += item.item_total
    end
    to_return
  end


end